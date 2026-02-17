import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../../services/order.service';
import { MenuService } from '../../services/menu.service';
import { NotificationService } from '../../services/notification.service';
import { OrderRequest, OrderResponse, DeliveryMode } from '../../models/order.model';
import { MenuItem } from '../../models/menu-item.model';

@Component({
    selector: 'app-orders',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
    <div class="page-container">
      <div class="page-header">
        <h1>Place an Order</h1>
        <p>Select items and schedule your delivery</p>
      </div>

      <div class="content-grid">
        <!-- Order form -->
        <div class="card">
          <h2 class="section-title">Order Details</h2>
          <form (ngSubmit)="onSubmit()" #orderForm="ngForm">
            <div class="form-group">
              <label for="customerCode">Customer Code</label>
              <input
                id="customerCode"
                class="form-control"
                [(ngModel)]="form.customerCode"
                name="customerCode"
                placeholder="Paste your customer code"
                required
                #cc="ngModel"
                [class.invalid]="cc.invalid && cc.touched"
              />
              @if (cc.invalid && cc.touched) {
                <div class="error-message">Customer code is required</div>
              }
            </div>

            <div class="form-row">
              <div class="form-group">
                <label for="deliveryTime">Delivery Time</label>
                <input
                  id="deliveryTime"
                  type="time"
                  class="form-control"
                  [(ngModel)]="deliveryTimeStr"
                  name="deliveryTime"
                  required
                  #dt="ngModel"
                  [class.invalid]="dt.invalid && dt.touched"
                />
                @if (dt.invalid && dt.touched) {
                  <div class="error-message">Delivery time is required</div>
                }
              </div>
              <div class="form-group">
                <label>Delivery Mode</label>
                <div class="mode-toggle">
                  <button
                    type="button"
                    class="mode-btn"
                    [class.active]="form.deliveryMode === 'DELIVERY'"
                    (click)="form.deliveryMode = DeliveryMode.DELIVERY"
                  >
                    🚚 Delivery
                  </button>
                  <button
                    type="button"
                    class="mode-btn"
                    [class.active]="form.deliveryMode === 'PICKUP'"
                    (click)="form.deliveryMode = DeliveryMode.PICKUP"
                  >
                    🏪 Pickup
                  </button>
                </div>
              </div>
            </div>

            <!-- Menu items selection -->
            <div class="form-group">
              <label>Select Menu Items</label>
              @if (menuLoading) {
                <p class="hint">Loading menu...</p>
              } @else if (menuItems.length === 0) {
                <p class="hint">No menu items available</p>
              } @else {
                <div class="items-list">
                  @for (item of menuItems; track item.id) {
                    <label class="item-check" [class.selected]="isSelected(item.id)" [class.disabled]="!item.available">
                      <input
                        type="checkbox"
                        [checked]="isSelected(item.id)"
                        [disabled]="!item.available"
                        (change)="toggleItem(item.id)"
                      />
                      <span class="item-info">
                        <span class="item-name">{{ item.name }}</span>
                        <span class="item-price">{{ item.price | number:'1.2-2' }} MAD</span>
                      </span>
                      @if (!item.available) {
                        <span class="badge badge-error">Sold Out</span>
                      }
                    </label>
                  }
                </div>
              }
              @if (submitted && form.menuItemIds.length === 0) {
                <div class="error-message">Select at least one item</div>
              }
            </div>

            <button
              type="submit"
              class="btn btn-primary btn-full"
              [disabled]="orderForm.invalid || form.menuItemIds.length === 0 || loading"
            >
              {{ loading ? 'Placing Order...' : '📦 Place Order' }}
            </button>
          </form>
        </div>

        <!-- Order confirmation -->
        @if (orderResult) {
          <div class="card confirmation-card">
            <div class="confirm-icon">✅</div>
            <h2 class="section-title">Order Confirmed!</h2>

            <div class="order-details">
              <div class="detail-row">
                <span class="detail-label">Order #</span>
                <span class="detail-value highlight">{{ orderResult.id }}</span>
              </div>
              <div class="detail-row">
                <span class="detail-label">Customer</span>
                <span class="detail-value">{{ orderResult.customerName }}</span>
              </div>
              <div class="detail-row">
                <span class="detail-label">Delivery Time</span>
                <span class="detail-value">{{ orderResult.deliveryTime }}</span>
              </div>
              <div class="detail-row">
                <span class="detail-label">Mode</span>
                <span class="detail-value">
                  {{ orderResult.deliveryMode === 'DELIVERY' ? '🚚 Delivery' : '🏪 Pickup' }}
                </span>
              </div>
              <div class="detail-row items-detail">
                <span class="detail-label">Items</span>
                <div class="items-tags">
                  @for (name of orderResult.menuItemNames; track name) {
                    <span class="item-tag">{{ name }}</span>
                  }
                </div>
              </div>
            </div>

            <button class="btn btn-secondary btn-full" (click)="resetForm()">
              Place Another Order
            </button>
          </div>
        }
      </div>
    </div>
  `,
    styles: [`
    .content-grid {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 24px;
      align-items: start;
    }

    .section-title {
      font-size: 1.15rem;
      font-weight: 600;
      margin-bottom: 24px;
    }

    .mode-toggle {
      display: flex;
      gap: 8px;
    }

    .mode-btn {
      flex: 1;
      padding: 10px 16px;
      background: var(--bg-input);
      border: 1px solid var(--border-color);
      border-radius: var(--radius-sm);
      color: var(--text-secondary);
      font-family: inherit;
      font-size: 0.9rem;
      cursor: pointer;
      transition: var(--transition);
    }

    .mode-btn:hover {
      border-color: rgba(255, 255, 255, 0.15);
    }

    .mode-btn.active {
      background: rgba(255, 140, 66, 0.12);
      border-color: var(--accent-color);
      color: var(--accent-color);
      font-weight: 600;
    }

    .hint {
      color: var(--text-muted);
      font-size: 0.85rem;
      padding: 12px 0;
    }

    .items-list {
      display: flex;
      flex-direction: column;
      gap: 6px;
      max-height: 300px;
      overflow-y: auto;
      padding-right: 4px;
    }

    .item-check {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 10px 14px;
      background: var(--bg-input);
      border: 1px solid var(--border-color);
      border-radius: var(--radius-sm);
      cursor: pointer;
      transition: var(--transition);
    }

    .item-check:hover:not(.disabled) {
      border-color: rgba(255, 255, 255, 0.15);
    }

    .item-check.selected {
      background: rgba(255, 140, 66, 0.08);
      border-color: var(--accent-color);
    }

    .item-check.disabled {
      opacity: 0.4;
      cursor: not-allowed;
    }

    .item-check input[type="checkbox"] {
      accent-color: var(--accent-color);
      width: 16px;
      height: 16px;
    }

    .item-info {
      flex: 1;
      display: flex;
      justify-content: space-between;
    }

    .item-name {
      font-weight: 500;
      font-size: 0.9rem;
    }

    .item-price {
      color: var(--text-secondary);
      font-size: 0.85rem;
    }

    .btn-full {
      width: 100%;
      justify-content: center;
      margin-top: 8px;
    }

    .confirmation-card {
      text-align: center;
      animation: fadeIn 0.4s ease;
    }

    .confirm-icon {
      font-size: 3rem;
      margin-bottom: 8px;
    }

    .order-details {
      text-align: left;
      margin: 20px 0;
    }

    .detail-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px 0;
      border-bottom: 1px solid var(--border-color);
    }

    .detail-label {
      color: var(--text-secondary);
      font-size: 0.85rem;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .detail-value {
      font-weight: 500;
    }

    .detail-value.highlight {
      font-size: 1.1rem;
      color: var(--accent-color);
      font-weight: 700;
    }

    .items-detail {
      flex-direction: column;
      align-items: flex-start;
      gap: 8px;
    }

    .items-tags {
      display: flex;
      flex-wrap: wrap;
      gap: 6px;
    }

    .item-tag {
      padding: 4px 12px;
      background: rgba(255, 140, 66, 0.1);
      border: 1px solid rgba(255, 140, 66, 0.2);
      border-radius: 20px;
      font-size: 0.8rem;
      color: var(--accent-color);
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    @media (max-width: 768px) {
      .content-grid { grid-template-columns: 1fr; }
    }
  `]
})
export class OrdersComponent implements OnInit {
    DeliveryMode = DeliveryMode;
    menuItems: MenuItem[] = [];
    menuLoading = false;
    loading = false;
    submitted = false;
    deliveryTimeStr = '';
    orderResult: OrderResponse | null = null;

    form: OrderRequest = {
        customerCode: '',
        deliveryTime: '',
        deliveryMode: DeliveryMode.DELIVERY,
        menuItemIds: []
    };

    constructor(
        private orderService: OrderService,
        private menuService: MenuService,
        private notification: NotificationService
    ) { }

    ngOnInit(): void {
        this.loadMenu();
    }

    loadMenu(): void {
        this.menuLoading = true;
        this.menuService.getMenuItems().subscribe({
            next: (items) => {
                this.menuItems = items.filter(i => i.available);
                this.menuLoading = false;
            },
            error: () => {
                this.notification.error('Failed to load menu');
                this.menuLoading = false;
            }
        });
    }

    isSelected(id: number): boolean {
        return this.form.menuItemIds.includes(id);
    }

    toggleItem(id: number): void {
        if (this.isSelected(id)) {
            this.form.menuItemIds = this.form.menuItemIds.filter(i => i !== id);
        } else {
            this.form.menuItemIds = [...this.form.menuItemIds, id];
        }
    }

    onSubmit(): void {
        this.submitted = true;
        if (this.form.menuItemIds.length === 0) return;

        this.loading = true;
        this.form.deliveryTime = this.deliveryTimeStr;

        this.orderService.createOrder(this.form).subscribe({
            next: (res) => {
                this.orderResult = res;
                this.notification.success('Order placed successfully!');
                this.loading = false;
            },
            error: (err) => {
                const msg = err.error?.message || 'Failed to place order';
                this.notification.error(msg);
                this.loading = false;
            }
        });
    }

    resetForm(): void {
        this.form = {
            customerCode: '',
            deliveryTime: '',
            deliveryMode: DeliveryMode.DELIVERY,
            menuItemIds: []
        };
        this.deliveryTimeStr = '';
        this.submitted = false;
        this.orderResult = null;
    }
}
