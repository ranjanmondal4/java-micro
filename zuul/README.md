This service was initially Designed for ZUUL.
But right now, it ZUUL + FeignClient. It takes all
request, authenticates it and use Feign Client mechainism 
to communicate with servers eg. User-Server.

Reason for Merging 2 services.
i. Using authentication, we get the User's information 
    and pass userId in the Urls.
