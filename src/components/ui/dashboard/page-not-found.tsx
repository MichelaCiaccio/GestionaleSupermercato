import { DashboardHeader } from './header';

export function PageNotFound() {
  return (
    <>
      <DashboardHeader title="Page Not Found" />

      <main className="flex flex-1 items-center px-4 py-12 sm:px-6 md:px-8 lg:px-12 xl:px-16">
        <div className="w-full space-y-6 text-center">
          <div className="space-y-3">
            <h1 className="text-4xl font-bold sm:text-5xl">404</h1>
            <p className="text-gray-500">
              Uh oh! The page you&apos;re looking for does not exist.
            </p>
          </div>
        </div>
      </main>
    </>
  );
}
