export enum DeliveryMode {
    DELIVERY = 'DELIVERY',
    PICKUP = 'PICKUP'
}

export interface OrderRequest {
    customerCode: string;
    deliveryTime: string;
    deliveryMode: DeliveryMode;
    menuItemIds: number[];
}

export interface OrderResponse {
    id: number;
    customerName: string;
    deliveryTime: string;
    deliveryMode: DeliveryMode;
    menuItemNames: string[];
    createdAt: string;
}
