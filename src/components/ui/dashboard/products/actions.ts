'use server';

import db from '@/lib/db';
import { ProductSchema, ProductValues } from '@/lib/entities/product';
import { RawFormData } from '@/types/utils';
import { revalidatePath } from 'next/cache';

export type ProductFormState = {
  message: string;
  values: ProductValues;
  errors?: Record<string, { message: string }>;
  success: boolean;
};

export async function createProduct(
  prevState: ProductFormState,
  formData: FormData
): Promise<ProductFormState> {
  const rawFormData = {
    name: formData.get('name'),
    sellingPrice: formData.get('sellingPrice'),
    category: formData.get('category'),
  } as RawFormData<ProductValues>;

  const validatedFields = ProductSchema.safeParse(rawFormData);
  if (!validatedFields.success) {
    const errors: ProductFormState['errors'] = {};
    for (const { path, message } of validatedFields.error.issues || []) {
      errors[path.join('.')] = { message };
    }
    return {
      errors,
      success: false,
      message: 'Missing or invalid fields. Failed to create new product.',
      values: {
        name: rawFormData.name ?? '',
        category: rawFormData.category ?? '',
        sellingPrice: parseFloat(rawFormData.sellingPrice ?? ''),
      },
    };
  }

  const { name, sellingPrice, category } = validatedFields.data;
  const sellingPriceInCents = sellingPrice * 100;

  let message: string;
  let success = false;
  try {
    message = await db.products.create({
      name,
      sellingPrice: sellingPriceInCents,
      category: { name: category },
    });
    success = true;
  } catch {
    message = 'Internal server error. Failed to create new product.';
  }

  revalidatePath('/dashboard/products');

  return {
    success,
    message,
    values: { name: '', sellingPrice: 0, category: '' },
  };
}

export async function deleteProduct(
  id: number
): Promise<Omit<ProductFormState, 'values' | 'errors'>> {
  let message: string;
  let success = false;

  try {
    message = await db.products.deleteById(id);
    success = true;
  } catch {
    message = 'Internal server error. Failed to delete product.';
  }

  revalidatePath('/dashboard/products');

  return {
    message,
    success,
  };
}
