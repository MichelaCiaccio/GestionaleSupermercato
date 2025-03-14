import { Breadcrumb } from '@/components/ui/breadcrumbs';
import { DashboardHeader } from '@/components/ui/dashboard/header';
import { CreateProductForm } from '@/components/ui/dashboard/products/create-form';

const breadcrumbs: Breadcrumb[] = [
  {
    label: 'Products',
    href: '/dashboard/products',
  },
  {
    label: 'Create Product',
  },
];

export default function CreateProductPage() {
  return (
    <>
      <DashboardHeader breadcrumbs={breadcrumbs} />

      <main className="grid gap-6 p-6">
        <CreateProductForm />
      </main>
    </>
  );
}
