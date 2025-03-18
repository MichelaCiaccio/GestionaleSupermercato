# NextJS Table Logic Brainstorming

**Objective:** To implement skeleton loading on the table itself and have control over the table header (which has input) and table footer (pagination).

**Problem:** The current code implementation is too coupled because they all depend on the table variable by the useReactTable hook.

## Solutions

1. One naive solution is to use a useState hook which holds the loading state of fetching table data. This is naive because I don't believe this is industry standard especially in the context of NextJS.

2. Another plausible solution is to use NextJS caching system, however, as of Next15, caching is now disabled by default and they're planning to change the caching system in the future. This is a solution because each table component (header, body, footer) can fetch the data by themselves because fetching will be done once because of the caching system. However, there are two caveats: First, it might produce a waterfall effect (but since React is smart, I don't think so) and second, pretty unstable of a solution since the caching system by NextJS wil change. Another major reason why I can't choose this solution is because NextJS' caching system works with the fetch API but I'm using Axios which is NOT built around the fetch API.

3. Use TanStack Query which basically is an extension of the second solution but better in the sense that caching is controlled by TanStack Query than NextJS and it works with Axios too. The only thing is... TanStack Query is a tool for the frontend. Not to say it cannot be used in the backend but for what's worth, that seems like a counter pattern for the philosophy of NextJS (I'm not taking about their caching system but more like, NextJS is built around Server components).

4. **SOLUTION:** Decouple the table input filter itself from the table by actually decoupling it from the table variable by useReactTable. How? By using query parameters instead. As the user types in, the query is reflected in the site's url (which is best practice!) and the table gets its query data from the url. This is also the best solution as querying is handled separately in the sense that it should be done by the backend (since it's faster too, no need to dump all table data and processing in the frontend). And useReactTable's job is to only show these table data.

**Reflection:** I was trying to build upon useReactTable hook to handle table data processing for me but actually, why should it handle it? The fourth solution is inspired by how NextJS's foundation course handled input query upon a table. Why did they use query parameters? Because imagine you have a specific query setup you did in the site, of course, you can just quickly bookmark the url and you'll get the same query setup back. This also decouples data querying and data processing!
