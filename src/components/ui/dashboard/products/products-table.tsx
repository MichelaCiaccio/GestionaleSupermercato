import { columns } from './products-column';
import { ProductsDataTable } from './products-data-table';

export function ProductsTable() {
  return <ProductsDataTable columns={columns} data={[]} />;
}
