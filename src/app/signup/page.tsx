import { SignUpForm } from '@/components/ui/signup/form';
import { Metadata } from 'next';

export const metadata: Metadata = {
  title: 'Sign Up',
};

export default function Page() {
  return (
    <div className="flex min-h-svh w-full items-center justify-center p-6 md:p-10">
      <div className="w-full max-w-sm">
        <SignUpForm />
      </div>
    </div>
  );
}
