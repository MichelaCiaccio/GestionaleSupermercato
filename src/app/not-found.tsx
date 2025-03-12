import { Button } from '@/components/ui/button';
import { DarkModeToggle } from '@/components/ui/dark-mode-toggle';
import Link from 'next/link';

export default function NotFound() {
  return (
    <div className="relative flex min-h-screen items-center px-4 py-12 sm:px-6 md:px-8 lg:px-12 xl:px-16">
      <div className="fixed top-5 right-5">
        <DarkModeToggle />
      </div>

      <div className="w-full space-y-6 text-center">
        <div className="space-y-3">
          <h1 className="text-4xl font-bold sm:text-5xl">404</h1>
          <p className="text-gray-500">
            Uh oh! The page you&apos;re looking for does not exist.
          </p>
        </div>
        <Button type="button" className="h-auto px-8 py-2.5" asChild>
          <Link href="/login">Go to login page</Link>
        </Button>
      </div>
    </div>
  );
}
