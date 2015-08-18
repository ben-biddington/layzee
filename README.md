# layzee

- [ ] As a User I can view a list of #lazyweb questions

- [ ] As a User I can select a specific #lazyweb question and see the whole conversation around that tweet (@replies)

- [ ] As a the User who asked the original question I can mark an @reply as the best response

# Installation

First install [leiningen](http://leiningen.org/).

```
$ lein deps
```

# Example

You'll need a twitter application so you can connect using [application only authentication](https://dev.twitter.com/oauth/application-only).

And then set two environment variables taken from your [Keys and Access Tokens screen](https://apps.twitter.com/app/8673064/keys):

```
$ export TWITTER_CONSUMER_KEY=xxx; export TWITTER_CONSUMER_SECRET=xxx
```

```
$ lein run
Searched for <100> <#lazyweb> mentions and found <13> results (filtered)
[Tue Aug 18 00:57:11 +0000 2015] Can anyone recommend a pair of wrap around headphones? I’ve been using a pair of these for 5+ years now #lazyweb http://t.co/38CEf1oBJJ -- Sir Tait of Brown
[Tue Aug 18 00:40:25 +0000 2015] #lazyweb -- Travis Berry
[Tue Aug 18 00:30:04 +0000 2015] Hey, #lazyweb…Is there a simple way to mark a record as #immutable in #Ruby on #Rails? I can't find a gem or macro for the life of me… #RoR -- ¯\_(ಠ_ಠ)_/¯ kmwhite
[Mon Aug 17 22:28:35 +0000 2015] Which historical figure has had the most public schools named after him/her? #lazyweb -- Jon Becker
[Mon Aug 17 21:53:38 +0000 2015] Dear #LazyWeb, I’m looking for an all-in-one SaaS tool for data crunching, visualizing &amp; reporting. Does it exist, do you know a good one? -- Julien Phalip
[Mon Aug 17 21:06:41 +0000 2015] dear #lazyweb, hol van a keletihez közel ügyeletes gyógyszertár? (tudom, google a barátom, de beszart a net és kb. csak a twitter app megy) -- esztereleonóra
[Mon Aug 17 20:34:44 +0000 2015] Hit me with some NET Framework value types known to be immutable. I've got string, TimeStamp, object and DateTime #lazyweb -- Robert Friberg
[Mon Aug 17 20:10:10 +0000 2015] A sort of #lazyweb question... anyone know if it's possible to run PHP via Dropbox? I've read it's unlikely but double-checking #gistribe -- John Van Hoesen
[Mon Aug 17 19:18:30 +0000 2015] Hey, #rails, is there a better gem/process for managing locales? Don't want full-page HTML in .yml files if possible. #lazyweb -- Benjamin Borowski
[Mon Aug 17 19:07:33 +0000 2015] Anyone aware of a way to get grunt to output stack dumps using sourcemaps? #lazyweb #grunt #javascript -- Javascript Digest
[Mon Aug 17 18:57:14 +0000 2015] Anyone aware of a way to get grunt to output stack dumps using sourcemaps? #lazyweb #grunt #javascript -- Steve Ognibene
[Mon Aug 17 18:52:48 +0000 2015] Freakin' love Amazon. Just ordered new RAM for my iMac. Being delivered TODAY. For FREE. #lazyweb -- Cory Watilo
[Mon Aug 17 18:51:07 +0000 2015] Dear #lazyWeb, which class in Symfony does form data (string) gets converted into their corresponding data types for proper Type validation? -- Guilherme Blanco
```
