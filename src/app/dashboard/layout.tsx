import { auth } from '@/auth';
import { AppSidebar } from '@/components/ui/dashboard/app-sidebar';
import { SidebarProvider } from '@/components/ui/sidebar';
import { ReactNode } from 'react';

export default async function DashboardLayout({
  children,
}: {
  children: ReactNode;
}) {
  const { user } = (await auth())!;
  const initials =
    user.name.slice(0, 1).toUpperCase() +
    user.surname.slice(0, 1).toUpperCase();

  return (
    <SidebarProvider>
      <AppSidebar
        user={{
          name: `${user.name} ${user.surname}`,
          avatar: '/',
          avatarFallback: initials,
          email: user.email,
        }}
      />

      <div className="flex flex-1 flex-col">{children}</div>
    </SidebarProvider>
  );
}
