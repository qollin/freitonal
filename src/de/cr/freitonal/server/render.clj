(ns de.cr.freitonal.server.render)

(defn create-string-from-ordinal-and-subordinal [ordinal sub-ordinal] 
  (str 
    ordinal 
    (if (and (not (nil? sub-ordinal)) (re-matches #"[^ ]" sub-ordinal)) 
      (str "-" sub-ordinal))))

(defn create-ordinal-and-subordinal-from-string [string]
  (.split string "-"))