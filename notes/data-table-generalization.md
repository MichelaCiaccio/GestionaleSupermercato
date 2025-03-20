# Data Table Generalization

_March 20, 2025_

**Objective:** Generalize Tanstack Table with NextJS.

**Abstract:** Shadcn uses Tanstack Table because it offers rich features on table's data processing like filtering, sorting, and pagination. Therefore, Tanstack Table is really a headless UI library. Headless UI means that it does things in the background while it's shadcn that actually shows the visual elements. Is using a convoluted and over-engineered table my suit case? To be honest, I learned that too late. However, I do need it since the features I need from the backend are still in progress, suffice to say I'm handling table data processing thanks to Tanstack Table. How about when the backend has the features I need then? Well, I'd still use Tanstack Table since it opens the path to further data processing if needed and it has a design philosophy which makes single responsibility principle shine by extracting each cell into so-called `columns`. The goal of generalizing Tanstack Table and NextJS is to provide a seamless way to create tables handling data fetching, error handling, and skeleton loading. Needless to say, cache invalidation is part of this process as well.

## Solution

After thinking of a decent solution and implementing it, what shadcn said echoes back to me:

> _"Every data table or datagrid I've created has been unique. They all behave differently, have specific sorting and filtering requirements, and work with different data sources."_ (shadcn, [Data Table Introduction](https://ui.shadcn.com/docs/components/data-table))

Long story short, it is **hard** to generalize tables as they differ one way or the other.

This is the workflow I've come up with and it is no way as elegant as I thought it should be:

1. Create table columns:

```tsx
'use client';

const [columns, skeletonColumns] = new ColumnsBuilder<Data, DataSkeleton>()
  .addColumn(
    {
      accessorKey: 'key',
      header: () => <Head />,
      cell: () => <Cell />,
    },
    {
      accessorKey: 'key',
      header: () => <HeadSkeleton />,
      cell: () => <CellSkeleton />,
    }
  )
  // add more columns here...
  .build();

export { columns, skeletonColumns };
```

I chose a builder pattern approach. This is so that the actual cell element and skeleton element are near each other: so you don't forget setting up its skeleton counterpart.

2. Create `Table` and `SkeletonTable`:

```tsx
// table.tsx
import { columns } from './columns';

export async function Table() {
  const [data, isError] = await fetchData();

  return <DataTable columns={columns} data={data} isError={isError} />;
}
```

Notice how `isError` is passed there.

```tsx
// table-skeleton.tsx
import { columns } from './columns';

export async function Table() {
  const data = await generateSkeletonData();

  return <DataTableSkeleton columns={columns} data={data} />;
}
```

3. Finally, use it in page:

```tsx
import { Table } from './table.tsx';
import { TableSkeleton } from './table-skeleton.tsx';

export default async function Page() {
  return (
    <main>
      <Suspense fallback={<TableSkeleton />}>
        <Table />
      </Suspense>
    </main>
  </>
  );
}

```
