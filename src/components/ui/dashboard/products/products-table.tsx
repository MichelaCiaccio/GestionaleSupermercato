import db from '@/lib/db';
import { columns } from './products-column';
import { ProductsDataTable } from './products-data-table';

export async function ProductsTable() {
  const products = await db.products.getAll();

  return <ProductsDataTable columns={columns} data={products} />;
}
