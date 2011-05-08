(ns de.cr.freitonal.server.render)

(defn create-string-from-ordinal-and-subordinal [ordinal sub-ordinal] 
  (if (and (nil? ordinal) (nil? sub-ordinal))
    nil
    (str 
      ordinal 
      (if (and sub-ordinal (re-matches #"[^ ]" sub-ordinal)) 
        (str "-" sub-ordinal)))))

(defn create-ordinal-and-subordinal-from-string [string]
  (.split string "-"))