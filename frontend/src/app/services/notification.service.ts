import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

export interface Toast {
    message: string;
    type: 'success' | 'error' | 'info';
    id: number;
}

@Injectable({ providedIn: 'root' })
export class NotificationService {
    private counter = 0;
    private toastsSubject = new Subject<Toast>();
    private dismissSubject = new Subject<number>();

    toasts$ = this.toastsSubject.asObservable();
    dismiss$ = this.dismissSubject.asObservable();

    success(message: string): void {
        this.show(message, 'success');
    }

    error(message: string): void {
        this.show(message, 'error');
    }

    info(message: string): void {
        this.show(message, 'info');
    }

    dismiss(id: number): void {
        this.dismissSubject.next(id);
    }

    private show(message: string, type: Toast['type']): void {
        const id = ++this.counter;
        this.toastsSubject.next({ message, type, id });
        setTimeout(() => this.dismiss(id), 4000);
    }
}
