import { items } from '@/components/ui/dashboard/app-sidebar';
import { PageInProgress } from '@/components/ui/dashboard/page-in-progress';
import { notFound } from 'next/navigation';

export default async function Page(props: {
  params: Promise<{ page: string }>;
}) {
  const params = await props.params;
  const page = params.page;

  if (!isPageInProgress(page)) {
    notFound();
  }

  return <PageInProgress />;
}

function isPageInProgress(page: string): boolean {
  const pageUrl = `/${page}`;
  return items.some((item) => item.url === pageUrl);
}
