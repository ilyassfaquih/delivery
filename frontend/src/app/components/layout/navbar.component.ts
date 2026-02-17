import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
    selector: 'app-navbar',
    standalone: true,
    imports: [RouterLink, RouterLinkActive],
    template: `
    <nav class="navbar">
      <div class="navbar-inner">
        <a class="logo" routerLink="/">
          <span class="logo-icon">🍕</span>
          <span class="logo-text">DeliverEase</span>
        </a>
        <div class="nav-links">
          <a routerLink="/customers" routerLinkActive="active" class="nav-link">
            <span class="nav-icon">👤</span> Customers
          </a>
          <a routerLink="/menu" routerLinkActive="active" class="nav-link">
            <span class="nav-icon">🍽️</span> Menu
          </a>
          <a routerLink="/orders" routerLinkActive="active" class="nav-link">
            <span class="nav-icon">📦</span> Orders
          </a>
        </div>
      </div>
    </nav>
  `,
    styles: [`
    .navbar {
      position: sticky;
      top: 0;
      z-index: 100;
      background: rgba(15, 15, 20, 0.85);
      backdrop-filter: blur(20px);
      border-bottom: 1px solid var(--border-color);
    }

    .navbar-inner {
      max-width: 1100px;
      margin: 0 auto;
      padding: 0 24px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      height: 64px;
    }

    .logo {
      display: flex;
      align-items: center;
      gap: 10px;
      text-decoration: none;
      color: var(--text-primary);
    }

    .logo-icon { font-size: 1.5rem; }

    .logo-text {
      font-size: 1.25rem;
      font-weight: 700;
      background: var(--accent-gradient);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .nav-links {
      display: flex;
      gap: 4px;
    }

    .nav-link {
      display: flex;
      align-items: center;
      gap: 6px;
      padding: 8px 16px;
      border-radius: var(--radius-sm);
      text-decoration: none;
      color: var(--text-secondary);
      font-size: 0.9rem;
      font-weight: 500;
      transition: var(--transition);
    }

    .nav-link:hover {
      color: var(--text-primary);
      background: rgba(255, 255, 255, 0.06);
    }

    .nav-link.active {
      color: var(--accent-color);
      background: rgba(255, 140, 66, 0.1);
    }

    .nav-icon { font-size: 1.1rem; }
  `]
})
export class NavbarComponent { }
