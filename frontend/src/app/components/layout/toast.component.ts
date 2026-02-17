import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Subscription } from 'rxjs';
import { NotificationService, Toast } from '../../services/notification.service';

@Component({
    selector: 'app-toast',
    standalone: true,
    imports: [CommonModule],
    template: `
    <div class="toast-container">
      @for (toast of toasts; track toast.id) {
        <div class="toast toast-{{ toast.type }}" (click)="remove(toast.id)">
          {{ toast.message }}
        </div>
      }
    </div>
  `
})
export class ToastComponent implements OnInit, OnDestroy {
    toasts: Toast[] = [];
    private subs: Subscription[] = [];

    constructor(private notification: NotificationService) { }

    ngOnInit(): void {
        this.subs.push(
            this.notification.toasts$.subscribe(toast => this.toasts.push(toast)),
            this.notification.dismiss$.subscribe(id => this.remove(id))
        );
    }

    remove(id: number): void {
        this.toasts = this.toasts.filter(t => t.id !== id);
    }

    ngOnDestroy(): void {
        this.subs.forEach(s => s.unsubscribe());
    }
}
