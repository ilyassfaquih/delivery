import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MenuItem, MenuItemDTO } from '../models/menu-item.model';

@Injectable({ providedIn: 'root' })
export class MenuService {
    private readonly apiUrl = '/api/menu';

    constructor(private http: HttpClient) { }

    getMenuItems(query?: string): Observable<MenuItem[]> {
        let params = new HttpParams();
        if (query) {
            params = params.set('q', query);
        }
        return this.http.get<MenuItem[]>(this.apiUrl, { params });
    }

    addMenuItem(dto: MenuItemDTO): Observable<MenuItem> {
        return this.http.post<MenuItem>(this.apiUrl, dto);
    }
}
