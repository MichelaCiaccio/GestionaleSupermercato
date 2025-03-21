import { Category, CreateCategoryDTO } from './category';

export interface Product {
  id: number;
  name: string;
  sellingPrice: number;
  category: Category;
  stocks: [];
}

export type CreateProductDTO = Omit<Product, 'id' | 'stocks' | 'category'> & {
  category: CreateCategoryDTO;
};

export type UpdateProductDTO = CreateProductDTO;
