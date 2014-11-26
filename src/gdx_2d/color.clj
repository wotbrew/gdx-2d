(ns gdx-2d.color
  "Colors, many colors"
  (:import (com.badlogic.gdx.graphics Color)))

(def black (Color/BLACK))
(def blue (Color/BLUE))
(def clear (Color/CLEAR))
(def cyan (Color/CYAN))
(def dark-gray (Color/DARK_GRAY))
(def gray (Color/GRAY))
(def green (Color/GREEN))
(def light-gray (Color/LIGHT_GRAY))
(def magneta (Color/MAGENTA))
(def maroon (Color/MAROON))
(def navy (Color/NAVY))
(def olive (Color/OLIVE))
(def orange (Color/ORANGE))
(def pink (Color/PINK))
(def purple (Color/PURPLE))
(def red (Color/RED))
(def teal (Color/TEAL))
(def white (Color/WHITE))
(def yellow (Color/YELLOW))

(defn color
  "Creates a new color e.g
   (def red (Color. 1 0 0 1))"
  [r g b a]
  (Color. r g b a))