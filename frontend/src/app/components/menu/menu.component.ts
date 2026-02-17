import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MenuService } from '../../services/menu.service';
import { NotificationService } from '../../services/notification.service';
import { MenuItem, MenuItemDTO } from '../../models/menu-item.model';

@Component({
    selector: 'app-menu',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
    <div class="page-container">
      <div class="page-header">
        <h1>Menu Items</h1>
        <p>Browse the menu or add new dishes</p>
      </div>

      <!-- Search + Add toggle -->
      <div class="toolbar">
        <div class="search-box">
          <span class="search-icon">🔍</span>
          <input
            class="form-control search-input"
            placeholder="Search menu items..."
            [(ngModel)]="searchQuery"
            (ngModelChange)="onSearch()"
          />
        </div>
        <button class="btn btn-primary" (click)="showForm = !showForm">
          {{ showForm ? '✕ Close' : '+ Add Item' }}
        </button>
      </div>

      <!-- Add item form -->
      @if (showForm) {
        <div class="card form-card">
          <h2 class="section-title">New Menu Item</h2>
          <form (ngSubmit)="addItem()" #menuForm="ngForm">
            <div class="form-row">
              <div class="form-group">
                <label for="name">Item Name</label>
                <input
                  id="name"
                  class="form-control"
                  [(ngModel)]="newItem.name"
                  name="name"
                  placeholder="Margherita Pizza"
                  required
                  #nm="ngModel"
                  [class.invalid]="nm.invalid && nm.touched"
                />
              </div>
              <div class="form-group">
                <label for="price">Price (MAD)</label>
                <input
                  id="price"
                  type="number"
                  class="form-control"
                  [(ngModel)]="newItem.price"
                  name="price"
                  placeholder="45.00"
                  required
                  min="0.01"
                  step="0.01"
                  #pr="ngModel"
                  [class.invalid]="pr.invalid && pr.touched"
                />
              </div>
            </div>
            <div class="form-group toggle-group">
              <label class="toggle-label">
                <input type="checkbox" [(ngModel)]="newItem.available" name="available" />
                <span class="toggle-slider"></span>
                <span>Available</span>
              </label>
            </div>
            <button type="submit" class="btn btn-primary" [disabled]="menuForm.invalid || addingItem">
              {{ addingItem ? 'Adding...' : '🍽️ Add to Menu' }}
            </button>
          </form>
        </div>
      }

      <!-- Menu grid -->
      @if (loading) {
        <div class="loading-state">Loading menu items...</div>
      } @else if (items.length === 0) {
        <div class="empty-state">
          <span class="empty-icon">🍽️</span>
          <p>No menu items found</p>
        </div>
      } @else {
        <div class="cards-grid">
          @for (item of items; track item.id) {
            <div class="card menu-card" [class.unavailable]="!item.available">
              <div class="menu-card-header">
                <h3 class="item-name">{{ item.name }}</h3>
                <span class="badge" [class.badge-success]="item.available" [class.badge-error]="!item.available">
                  {{ item.available ? 'Available' : 'Sold Out' }}
                </span>
              </div>
              <div class="item-price">{{ item.price | number:'1.2-2' }} MAD</div>
            </div>
          }
        </div>
      }
    </div>
  `,
    styles: [`
    .toolbar {
      display: flex;
      gap: 16px;
      margin-bottom: 24px;
      align-items: center;
    }

    .search-box {
      flex: 1;
      position: relative;
    }

    .search-icon {
      position: absolute;
      left: 14px;
      top: 50%;
      transform: translateY(-50%);
      font-size: 1rem;
      z-index: 1;
    }

    .search-input {
      padding-left: 42px !important;
    }

    .form-card {
      margin-bottom: 24px;
      animation: fadeIn 0.3s ease;
    }

    .section-title {
      font-size: 1.1rem;
      font-weight: 600;
      margin-bottom: 20px;
    }

    .toggle-group {
      margin-bottom: 20px;
    }

    .toggle-label {
      display: flex;
      align-items: center;
      gap: 10px;
      cursor: pointer;
      font-size: 0.9rem;
      color: var(--text-secondary);
    }

    .toggle-label input[type="checkbox"] {
      width: 18px;
      height: 18px;
      accent-color: var(--accent-color);
    }

    .menu-card {
      cursor: default;
    }

    .menu-card.unavailable {
      opacity: 0.5;
    }

    .menu-card-header {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: 12px;
    }

    .item-name {
      font-size: 1.05rem;
      font-weight: 600;
    }

    .item-price {
      font-size: 1.25rem;
      font-weight: 700;
      background: var(--accent-gradient);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .loading-state, .empty-state {
      text-align: center;
      padding: 48px;
      color: var(--text-secondary);
    }

    .empty-icon {
      font-size: 3rem;
      display: block;
      margin-bottom: 12px;
    }

    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    @media (max-width: 640px) {
      .toolbar { flex-direction: column; }
    }
  `]
})
export class MenuComponent implements OnInit {
    items: MenuItem[] = [];
    searchQuery = '';
    showForm = false;
    loading = false;
    addingItem = false;
    newItem: MenuItemDTO = { name: '', price: 0, available: true };

    constructor(
        private menuService: MenuService,
        private notification: NotificationService
    ) { }

    ngOnInit(): void {
        this.loadItems();
    }

    loadItems(): void {
        this.loading = true;
        this.menuService.getMenuItems(this.searchQuery || undefined).subscribe({
            next: (items) => {
                this.items = items;
                this.loading = false;
            },
            error: () => {
                this.notification.error('Failed to load menu items');
                this.loading = false;
            }
        });
    }

    onSearch(): void {
        this.loadItems();
    }

    addItem(): void {
        this.addingItem = true;
        this.menuService.addMenuItem(this.newItem).subscribe({
            next: () => {
                this.notification.success('Menu item added!');
                this.newItem = { name: '', price: 0, available: true };
                this.showForm = false;
                this.addingItem = false;
                this.loadItems();
            },
            error: (err) => {
                const msg = err.error?.message || 'Failed to add item';
                this.notification.error(msg);
                this.addingItem = false;
            }
        });
    }
}
