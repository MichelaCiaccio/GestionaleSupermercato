export interface Category {
  id: string;
  name: string;
}

export type CreateCategoryDTO = Omit<Category, 'id'>;
export type UpdateCategoryDTO = Omit<Category, 'id'>;
