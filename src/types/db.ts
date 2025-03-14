export type Category = {
  id: string;
  name: string;
};

export type CreateCategoryDTO = Omit<Category, 'id'>;
export type UpdateCategoryDTO = Omit<Category, 'id'>;

export type Product = {
  id: number;
  name: string;
  sellingPrice: number;
  category: Category;
  stocks: [];
};

export type CreateProductDTO = Omit<Product, 'id' | 'stocks' | 'category'> & {
  category: CreateCategoryDTO;
};

export type UpdateProductDTO = Partial<Omit<Product, 'id'>>;
