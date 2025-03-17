import db from '@/lib/db';
import { columns } from './products-column';
import { ProductsDataTable } from './products-data-table';
import { Product } from '@/types/db';

export async function ProductsTable() {
  let products: Product[];

  try {
    products = await db.products.getAll();
  } catch {
    products = [];
  }

  return <ProductsDataTable columns={columns} data={products} />;
}
