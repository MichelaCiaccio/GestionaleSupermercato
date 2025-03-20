# Cache Revalidation

Okay for some reason, this is definitely skill issue and ignorance, I need to learn more about caching works in NextJS.

**Problem:** Adding a new product does not update the products count in the overview dashboard.

**Cause:** Probably caused by NextJS caching the overview page. In fact, when I build the project to production, it tells me that the overview page is in static rendering.

## Solutions

1. I thought of using Tanstack Query but it seems unnecessary for a server-able framework like NextJS. Since I make db requests from server components, there's no need for a client tool like Tanstack Query unless I wish to move the fetching logic to client which I don't have plan to since it's a counter-pattern to the philosophy of Next.

2. Use NextJS canary version (currently it's March 20th, 2025) to use DynamicIO. In the real world, I don't think it's a good choice to start implementing/using beta versions of the framework so I opted not to upgrade to the canary version.

3. Use `revalidateTag` from `next/cache`. Before I start porting code from Axios to using JS's fetch API, I first tried if it will work. Alas, it still doesn't work. The overview page is still statically rendered and it gives me the cached page unless if I change something in the products page which is a big inconvenience for the user.

4. **SOLUTION:** Wait for the next stable version of NextJS with dynamicIO. For now, I'll revalidate paths manually:

```ts
revalidatePath('/dashboard');
revalidatePath('/dashboard/products');
```

It's really ugly and hacky but it does the job done.

**Reflection:** I have no idea why `revalidateTag` isn't working properly. I thought it will revalidate the cached rendered overview page but nope. I thought this way because I find it strange how come `revalidatePath` works. Here's what I think, `revalidateTag` invalidates the cache used in `fetch` while `revalidatePath` invalidates the cached page. I surmise the resources (in fetch) and pages are two distinct things.
