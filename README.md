# crawler-wcContent: Efficient WeChat Public Account Article Crawler

## Project Overview

**crawler-wcContent** is tailored for the efficient extraction of articles from designated WeChat public accounts. As the need arose to showcase trending content on our in-house platform, this crawler emerges as the robust solution.

## Background

Traditionally, we'd showcase trending content by pulling data from platforms like Weibo or utilizing APIs from various other platforms. However, the current demand gravitates towards extracting articles from WeChat public accounts. Given that WeChat doesn't offer a direct API for this purpose, our quest for article retrieval commenced.

## Data Source Methods:

### 1. Internal Search:

Mostly, we've capitalized on the API provided by Sogou search (Baidu wasn't viable for our use case). Here's a step-by-step visual depiction:

![Sogou Search Example](https://github.com/lkh24/crawler-wcContent/assets/78600882/0239c3ae-5159-454b-90c9-ecc2007d54ae)

Using a university as a typical example, the content retrieved through this method is virtually everything Sogou has cataloged from WeChat public accounts. Further, we construct our query for the Sogou API:

![API Parameter Construction](https://github.com/lkh24/crawler-wcContent/assets/78600882/223f07a0-00ec-4f9c-8bd0-fd0666cb1f75)

Post this, we parse the 'link' tags to garner details like titles, URLs, cover images, and summaries. While this approach is straightforward and involves simple parameter analysis, its reliance on a search engine makes the order and immediacy of the results inconsistent.

### 2. Direct Access via Public Accounts:

Another promising avenue was directly through WeChat public accounts. However, it's not without its challenges. One needs to register a public account, and this process demands several corporate document approvals.

![Public Account Interface](https://github.com/lkh24/crawler-wcContent/assets/78600882/efd84b26-7b55-4c57-8196-a07a54071372)

Once data is accessed from individual pages, parsing and analysis allow the extraction of the desired content.

## Conclusion

This article elucidates the methods to acquire and process web-based data, emphasizing WeChat public account content retrieval. Techniques span from leveraging internal search engines to accessing direct content from public accounts. Furthermore, it deals with parsing web requests, analyzing page content, and circumventing anti-crawling measures. The additional focus on webpage design ensures the data is showcased effectively. This comprehensive guide serves as a reference for anyone looking to navigate the intricacies of web data extraction.
