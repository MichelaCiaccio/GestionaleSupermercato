import { suppliers } from './sample-data';
import { columns } from './suppliers-column';
import { SuppliersDataTable } from './suppliers-data-table';

export function SuppliersTable() {
  return <SuppliersDataTable columns={columns} data={suppliers} />;
}
