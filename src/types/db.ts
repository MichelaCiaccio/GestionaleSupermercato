export type Category = {
  id: string;
  name: string;
};

export type Product = {
  id: number;
  name: string;
  sellingPrice: number;
  category: Category;
  stocks: [];
};

export type CreateProductDTO = Omit<Product, 'id'>;
export type UpdateProductDTO = Partial<Omit<Product, 'id'>>;
