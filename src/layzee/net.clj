(ns layzee.net
  (:require [clj-http.util :as util]))

(defn %    [what] (util/url-encode (or what "")))
(defn %64  [what] (util/base64-encode (util/utf8-bytes what)))