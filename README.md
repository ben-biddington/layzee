# layzee

[![Build Status](https://api.travis-ci.org/ben-biddington/layzee.svg?branch=master)](http://travis-ci.org/ben-biddington/layzee)

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

And then either set two environment variables taken from your [Keys and Access Tokens screen](https://apps.twitter.com/app/8673064/keys):

```
$ export TWITTER_CONSUMER_KEY=xxx; export TWITTER_CONSUMER_SECRET=xxx
```

Or put them in a file called `.twitter`. See [`.twitter.example`](https://github.com/ben-biddington/layzee/blob/master/.twitter.example) for an example.

```

$ lein run
Searched for <100> <#lazyweb> mentions and found <81> results (filtered)
[18-08-15 02:56] https://twitter.com/325457693/status/633472387497902080           `bang`                         "#lazyweb"
[18-08-15 01:39] https://twitter.com/58180307/status/633453216609361920            Paul Wissam                    @_Kissthebottle #lazyweb haha Will have to find it myself -- after scouring through 13 different geoblocked versions
[18-08-15 00:57] https://twitter.com/18131313/status/633442482378772480            Sir Tait of Brown              Can anyone recommend a pair of wrap around headphones? I’ve been using a pair of these for 5+ years now #lazyweb http://t.co/38CEf1oBJJ
[18-08-15 00:40] https://twitter.com/19734134/status/633438263127109632            Travis Berry                   #lazyweb
[18-08-15 00:30] https://twitter.com/101385493/status/633435659303301120           ¯\_(ಠ_ಠ)_/¯ kmwhite            Hey, #lazyweb…Is there a simple way to mark a record as #immutable in #Ruby on #Rails? I can't find a gem or macro for the life of me… #RoR
[17-08-15 22:28] https://twitter.com/12363792/status/633405086438436864            Jon Becker                     Which historical figure has had the most public schools named after him/her? #lazyweb
[17-08-15 21:53] https://twitter.com/16176084/status/633396290001342465            Julien Phalip                  Dear #LazyWeb, I’m looking for an all-in-one SaaS tool for data crunching, visualizing &amp; reporting. Does it exist, do you know a good one?
[17-08-15 21:06] https://twitter.com/54399422/status/633384475146194944            esztereleonóra                 dear #lazyweb, hol van a keletihez közel ügyeletes gyógyszertár? (tudom, google a barátom, de beszart a net és kb. csak a twitter app megy)
[17-08-15 20:34] https://twitter.com/28348118/status/633376437492432897            Robert Friberg                 Hit me with some NET Framework value types known to be immutable. I've got string, TimeStamp, object and DateTime #lazyweb
[17-08-15 20:10] https://twitter.com/189712167/status/633370252693368832           John Van Hoesen                A sort of #lazyweb question... anyone know if it's possible to run PHP via Dropbox? I've read it's unlikely but double-checking #gistribe
[17-08-15 19:18] https://twitter.com/11973362/status/633357248891195392            Benjamin Borowski              Hey, #rails, is there a better gem/process for managing locales? Don't want full-page HTML in .yml files if possible. #lazyweb
[17-08-15 19:07] https://twitter.com/3243544179/status/633354496823922688          Javascript Digest              Anyone aware of a way to get grunt to output stack dumps using sourcemaps? #lazyweb #grunt #javascript
[17-08-15 18:57] https://twitter.com/615200517/status/633351900390420481           Steve Ognibene                 Anyone aware of a way to get grunt to output stack dumps using sourcemaps? #lazyweb #grunt #javascript
[17-08-15 18:52] https://twitter.com/11612592/status/633350784290189312            Cory Watilo                    Freakin' love Amazon. Just ordered new RAM for my iMac. Being delivered TODAY. For FREE. #lazyweb
[17-08-15 18:51] https://twitter.com/18475335/status/633350358144839680            Guilherme Blanco               Dear #lazyWeb, which class in Symfony does form data (string) gets converted into their corresponding data types for proper Type validation?
[17-08-15 18:40] https://twitter.com/73653/status/633347741092024320               J Herskowitz                   What’s the current best way to get Gmail to check other (IMAP) accounts more than once every hour? #lazyweb
[17-08-15 18:19] https://twitter.com/62313555/status/633342488632250373            Danny Hyun                     Is anyone using a cookie based session store with @springboot? #lazyweb
[17-08-15 17:39] https://twitter.com/306497372/status/633332293470433280           butt sandal                    #lazyweb Any good guides on speeding up PostgreSQL queries? Rails specific is especially helpful.
[17-08-15 16:51] https://twitter.com/109899498/status/633320233428754433           Nikoletta M.                   Kinek van közepes túrazsákja? :) #lazyweb #segítsnekem #meghálálom
[17-08-15 16:39] https://twitter.com/304067888/status/633317121922068480           ashley williams                what is the site with the open diversity stats? #lazyweb
```

For extra logging:

```
$ LOG=1 lein run
```

