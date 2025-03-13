import { DashboardHeader } from './header';

export function PageInProgress() {
  return (
    <>
      <DashboardHeader breadcrumbs={[{ label: 'Page In Development' }]} />

      <div className="flex flex-1 items-center px-4 py-12 sm:px-6 md:px-8 lg:px-12 xl:px-16">
        <div className="w-full space-y-6 text-center">
          <div className="space-y-3">
            <h1 className="text-4xl font-bold sm:text-5xl">In Development</h1>
            <p className="text-gray-500">
              Please wait! The page you&apos;re looking for is currently in
              development.
            </p>
          </div>
        </div>
      </div>
    </>
  );
}
