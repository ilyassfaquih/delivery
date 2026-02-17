import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../services/customer.service';
import { NotificationService } from '../../services/notification.service';
import { CustomerDTO, CustomerResponse } from '../../models/customer.model';

@Component({
    selector: 'app-customers',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
    <div class="page-container">
      <div class="page-header">
        <h1>Register Customer</h1>
        <p>Create a new customer account to start ordering</p>
      </div>

      <div class="content-grid">
        <!-- Registration form -->
        <div class="card form-card">
          <h2 class="card-title">New Customer</h2>
          <form (ngSubmit)="onSubmit()" #customerForm="ngForm">
            <div class="form-row">
              <div class="form-group">
                <label for="firstName">First Name</label>
                <input
                  id="firstName"
                  class="form-control"
                  [(ngModel)]="form.firstName"
                  name="firstName"
                  placeholder="John"
                  required
                  minlength="2"
                  #fn="ngModel"
                  [class.invalid]="fn.invalid && fn.touched"
                />
                @if (fn.invalid && fn.touched) {
                  <div class="error-message">First name is required (min 2 chars)</div>
                }
              </div>
              <div class="form-group">
                <label for="lastName">Last Name</label>
                <input
                  id="lastName"
                  class="form-control"
                  [(ngModel)]="form.lastName"
                  name="lastName"
                  placeholder="Doe"
                  required
                  minlength="2"
                  #ln="ngModel"
                  [class.invalid]="ln.invalid && ln.touched"
                />
                @if (ln.invalid && ln.touched) {
                  <div class="error-message">Last name is required (min 2 chars)</div>
                }
              </div>
            </div>

            <div class="form-group">
              <label for="email">Email</label>
              <input
                id="email"
                type="email"
                class="form-control"
                [(ngModel)]="form.email"
                name="email"
                placeholder="john.doe&#64;example.com"
                required
                email
                #em="ngModel"
                [class.invalid]="em.invalid && em.touched"
              />
              @if (em.invalid && em.touched) {
                <div class="error-message">A valid email is required</div>
              }
            </div>

            <div class="form-group">
              <label for="phone">Phone</label>
              <input
                id="phone"
                class="form-control"
                [(ngModel)]="form.phone"
                name="phone"
                placeholder="+212 600 000000"
                required
                #ph="ngModel"
                [class.invalid]="ph.invalid && ph.touched"
              />
              @if (ph.invalid && ph.touched) {
                <div class="error-message">Phone number is required</div>
              }
            </div>

            <button type="submit" class="btn btn-primary" [disabled]="customerForm.invalid || loading">
              {{ loading ? 'Registering...' : '✨ Register Customer' }}
            </button>
          </form>
        </div>

        <!-- Success result -->
        @if (createdCustomer) {
          <div class="card success-card">
            <div class="success-icon">🎉</div>
            <h2 class="card-title">Customer Registered!</h2>
            <div class="customer-details">
              <div class="detail-row">
                <span class="detail-label">Name</span>
                <span class="detail-value">{{ createdCustomer.firstName }} {{ createdCustomer.lastName }}</span>
              </div>
              <div class="detail-row">
                <span class="detail-label">Email</span>
                <span class="detail-value">{{ createdCustomer.email }}</span>
              </div>
              <div class="detail-row code-row">
                <span class="detail-label">Customer Code</span>
                <div class="code-box" (click)="copyCode()">
                  <code>{{ createdCustomer.code }}</code>
                  <span class="copy-hint">{{ copied ? '✓ Copied!' : '📋 Click to copy' }}</span>
                </div>
              </div>
            </div>
            <p class="code-note">Save this code — you'll need it to place orders!</p>
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

    .card-title {
      font-size: 1.15rem;
      font-weight: 600;
      margin-bottom: 24px;
      color: var(--text-primary);
    }

    .success-card {
      text-align: center;
      animation: fadeIn 0.4s ease;
    }

    .success-icon {
      font-size: 3rem;
      margin-bottom: 12px;
    }

    .customer-details {
      margin: 20px 0;
      text-align: left;
    }

    .detail-row {
      display: flex;
      justify-content: space-between;
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

    .code-row {
      flex-direction: column;
      gap: 8px;
    }

    .code-box {
      background: var(--bg-input);
      border: 1px dashed var(--accent-color);
      border-radius: var(--radius-sm);
      padding: 14px;
      cursor: pointer;
      transition: var(--transition);
      text-align: center;
    }

    .code-box:hover {
      background: rgba(255, 140, 66, 0.08);
    }

    .code-box code {
      display: block;
      font-size: 0.9rem;
      font-weight: 600;
      color: var(--accent-color);
      word-break: break-all;
      margin-bottom: 4px;
    }

    .copy-hint {
      font-size: 0.75rem;
      color: var(--text-muted);
    }

    .code-note {
      font-size: 0.85rem;
      color: var(--text-secondary);
      margin-top: 12px;
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
export class CustomersComponent {
    form: CustomerDTO = { firstName: '', lastName: '', email: '', phone: '' };
    loading = false;
    createdCustomer: CustomerResponse | null = null;
    copied = false;

    constructor(
        private customerService: CustomerService,
        private notification: NotificationService
    ) { }

    onSubmit(): void {
        this.loading = true;
        this.customerService.createCustomer(this.form).subscribe({
            next: (res) => {
                this.createdCustomer = res;
                this.notification.success('Customer registered successfully!');
                this.form = { firstName: '', lastName: '', email: '', phone: '' };
                this.loading = false;
            },
            error: (err) => {
                const msg = err.error?.message || 'Failed to register customer';
                this.notification.error(msg);
                this.loading = false;
            }
        });
    }

    copyCode(): void {
        if (this.createdCustomer) {
            navigator.clipboard.writeText(this.createdCustomer.code);
            this.copied = true;
            setTimeout(() => this.copied = false, 2000);
        }
    }
}
