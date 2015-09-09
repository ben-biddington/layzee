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

# Start the server

```

$ lein run -m layzee.adapters.web.server/main

```

Which makes the api available at `http://localhost:5000/api`, and the gui at ``http://localhost:5000/api` (and also at `https://layzee-web.herokuapp.com/api`)

# Example: Realtime

You may now monitor `lazyweb` mentions in real time:

```
$ lein run realtime clojure tdd hickey flag johnkey
Connecting to <https://stream.twitter.com/1.1/statuses/filter.json> with body <{"track" "clojure,tdd,hickey,flag,johnkey"}> (See https://dev.twitter.com/streaming/reference/post/statuses/filter)
Connected and listenting
[Mon Aug 31 23:45:04 +0000 2015] -- RT @2TAPU: Comisserations @shawnmoodie I thought your flag was fabulous and captured the essence of this whole flag debate http://t.co/iZLY…
[Mon Aug 31 23:45:04 +0000 2015] -- Do I remember this ? No... Did I lose my hat and flag? Yes😔 https://t.co/SY3Bjvcazx
[Mon Aug 31 23:45:04 +0000 2015] -- RT @Hypnoflag: WRONG CHOICE PUNY HUMAN https://t.co/PETqFmMre4
[Mon Aug 31 23:45:05 +0000 2015] -- RT @lukeappleby: Here are the official images showing the final four flag referendum choices in flight: #nzflag http://t.co/2MOasBbia2
[Mon Aug 31 23:45:05 +0000 2015] -- New Zealand flag options narrowed down to four | http://t.co/6tSuHh5bAw
[Mon Aug 31 23:45:08 +0000 2015] -- RT @2TAPU: Comisserations @shawnmoodie I thought your flag was fabulous and captured the essence of this whole flag debate http://t.co/iZLY…
[Mon Aug 31 23:45:09 +0000 2015] -- @nzhpolitics @nzherald Well, looks like I'm voting to keep the current flag.
[Mon Aug 31 23:45:10 +0000 2015] -- Бойцы АТО подняли флаг Украины над шахтой в Горловке http://t.co/bz46EngmiJ #Киев #Ukraine #Луганск #ДНР #Новороссия
[Mon Aug 31 23:45:10 +0000 2015] -- https://t.co/Gc4aBVmTi8 fired a white woman removed do not rent to the colored people" sign  hired white man confederate flag tatoo
[Mon Aug 31 23:45:11 +0000 2015] -- RT @Maori_Party: He aha ō koutou whakaaro? https://t.co/mZwF1Isgmi
[Mon Aug 31 23:45:12 +0000 2015] -- One day, you will be proud to worship me. One day. https://t.co/PihNRDCJqp
[Mon Aug 31 23:45:13 +0000 2015] -- I voted the current flag. Vote and let them know you're not buying! http://t.co/M2PypOYzkM
[Mon Aug 31 23:45:14 +0000 2015] -- @ibsss22 @YahooNoise I'd go with the guy who had hip replacement surgery. Flag football is no contact so he probably won't re-injure it.
[Mon Aug 31 23:45:14 +0000 2015] -- @thmslcn @_wild_world Winston wants everyone to spoil their ballot papers by writing CURRENT FLAG
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

# Twitter auth

[Looks for firehose mode you have to use normal oauth](https://dev.twitter.com/oauth/overview/authentication-by-api-family) rather than application-only bearer tokens (that the search API supports). We are using [bone](https://github.com/ben-biddington/bone) for oauth signing.


# Tools

Get [jq](http://xmodulo.com/how-to-parse-json-string-via-command-line-on-linux.html) and you can do something like:

```
curl -v https://layzee-web.herokuapp.com/api  | jq '.[0]' >> data.json

```

`data.json` contains:

```
{
  "in_reply_to_screen_name": null,
  "is_quote_status": false,
  "coordinates": null,
  "in_reply_to_status_id_str": null,
  "place": null,
  "geo": null,
  "in_reply_to_status_id": null,
  "entities": {
    "hashtags": [
      {
        "text": "lazyweb",
        "indices": [
          61,
          69
        ]
      }
    ],
    "symbols": [],
    "user_mentions": [],
    "urls": []
  },
  "source": "<a href=\"http://itunes.apple.com/us/app/twitter/id409789998?mt=12\" rel=\"nofollow\">Twitter for Mac</a>",
  "lang": "en",
  "in_reply_to_user_id_str": null,
  "id": 641033157383352300,
  "contributors": null,
  "truncated": false,
  "retweeted": false,
  "in_reply_to_user_id": null,
  "id_str": "641033157383352320",
  "favorited": false,
  "user": {
    "description": "https://t.co/YHNXd7KnI1",
    "profile_link_color": "ED145B",
    "profile_sidebar_border_color": "FFFFFF",
    "is_translation_enabled": false,
    "profile_image_url": "http://pbs.twimg.com/profile_images/630705993488293888/LPSuBILn_normal.png",
    "profile_use_background_image": true,
    "default_profile": false,
    "profile_background_image_url": "http://pbs.twimg.com/profile_background_images/631418763590504448/dfZrGlab.jpg",
    "is_translator": false,
    "profile_text_color": "000000",
    "profile_banner_url": "https://pbs.twimg.com/profile_banners/6000702/1439207675",
    "name": "Sara",
    "profile_background_image_url_https": "https://pbs.twimg.com/profile_background_images/631418763590504448/dfZrGlab.jpg",
    "favourites_count": 4505,
    "screen_name": "pikelet",
    "entities": {
      "url": {
        "urls": [
          {
            "url": "http://t.co/ZhYbT8jwYx",
            "expanded_url": "http://www.pikelet.nz",
            "display_url": "pikelet.nz",
            "indices": [
              0,
              22
            ]
          }
        ]
      },
      "description": {
        "urls": [
          {
            "url": "https://t.co/YHNXd7KnI1",
            "expanded_url": "https://www.youtube.com/watch?v=frBO8PkEQPA",
            "display_url": "youtube.com/watch?v=frBO8P…",
            "indices": [
              0,
              23
            ]
          }
        ]
      }
    },
    "listed_count": 41,
    "profile_image_url_https": "https://pbs.twimg.com/profile_images/630705993488293888/LPSuBILn_normal.png",
    "statuses_count": 33509,
    "has_extended_profile": true,
    "contributors_enabled": false,
    "following": null,
    "lang": "en",
    "utc_offset": 43200,
    "notifications": null,
    "default_profile_image": false,
    "profile_background_color": "3C3B3B",
    "id": 6000702,
    "follow_request_sent": null,
    "url": "http://t.co/ZhYbT8jwYx",
    "time_zone": "Wellington",
    "profile_sidebar_fill_color": "E0F5FF",
    "protected": false,
    "profile_background_tile": false,
    "id_str": "6000702",
    "geo_enabled": true,
    "location": "Auckland, New Zealand",
    "followers_count": 1108,
    "friends_count": 1211,
    "verified": false,
    "created_at": "Sun May 13 02:42:20 +0000 2007"
  },
  "metadata": {
    "iso_language_code": "en",
    "result_type": "recent"
  },
  "retweet_count": 0,
  "favorite_count": 0,
  "created_at": "Mon Sep 07 23:39:49 +0000 2015",
  "text": "What did the Greens do? ('Greens' is not a good search term) #lazyweb"
}

```

* First user: `curl -v https://layzee-web.herokuapp.com/api                       | jq '.[0] .user`
* All tweet messages: `curl -v https://layzee-web.herokuapp.com/api               | jq '.[] .text'`
* All tweet messages with username: `curl -v https://layzee-web.herokuapp.com/api | jq '[.[] | {tweet: .text, name: .user.screen_name}]'` 
* All tweet replies: `curl -v https://layzee-web.herokuapp.com/api                | jq '.[] .replies'`

There are [more examples](https://stedolan.github.io/jq/tutorial).