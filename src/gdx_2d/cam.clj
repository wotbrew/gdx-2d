(ns gdx-2d.cam
  (:import (com.badlogic.gdx.math Vector3)
           (com.badlogic.gdx.graphics Camera)))

(defn move!
  [^Camera cam x y]
  (.set (.position cam)
        x
        y
        0))

(defn update!
  [^Camera cam]
  (.update cam))

(defn project
  ([cam [x y]]
   (project cam x y))
  ([^Camera cam x y]
   (let [vec (Vector3. x y 1)]
     (.project cam vec)
     [(int (.x vec))
      (int (.y vec))])))

(defn unproject
  ([cam [x y]]
   (unproject cam x y))
  ([^Camera cam x y]
   (let [vec (Vector3. x y 1)]
     (.unproject cam vec)
     [(int (.x vec))
      (int (.y vec))])))