import { items } from '@/components/ui/dashboard/app-sidebar';
import { PageInProgress } from '@/components/ui/dashboard/page-in-progress';
import { notFound } from 'next/navigation';

export default async function NotFound(props: {
  params: Promise<{ page: string }>;
}) {
  const params = await props.params;
  const page = params.page;

  if (!isValidDashboardPage(page)) {
    notFound();
  }

  return <PageInProgress />;
}

export function isValidDashboardPage(page: string): boolean {
  const pageUrl = `/${page}`;
  console.log(pageUrl);

  // check if sidebar item link
  // in the future add more for specific esisting pages
  return items.some((item) => item.url === pageUrl);
}
