import { CardWrapper } from '@/components/ui/dashboard/overview/CardWrapper';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Overview',
};

export default function DashboardPage() {
  return (
    <main className="p-6">
      <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
        <CardWrapper />
      </div>
    </main>
  );
}
