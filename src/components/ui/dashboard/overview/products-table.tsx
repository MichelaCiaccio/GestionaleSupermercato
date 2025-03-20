import { columns } from './products-column';
import { DataTable } from '../../data-table';

export async function ProductsTable() {
  return <DataTable label="products" columns={columns} data={[]} />;
}
