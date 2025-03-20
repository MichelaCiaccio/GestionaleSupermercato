import { columns } from './sales-columns';
import { DataTable } from '../../data-table';

export async function SalesTable() {
  return <DataTable label="recent sales" columns={columns} data={[]} />;
}
