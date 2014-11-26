(ns rendering
  (:import (com.badlogic.gdx.graphics Color))
  (:require [clojure.test :refer :all]
            [gdx-loopy.core :refer :all]
            [gdx-2d.core :refer :all]
            [gdx-2d.pixmap :as pm]
            [gdx-2d.color :as color]))

(defonce renderer (atom (fn [])))

(defmacro every-frame
          [& body]
          `(reset! renderer (fn [] ~@body)))

(defonce loop*
         (loop! (fn [] (@renderer))))

(defonce white-square-pm
         @(on-render-thread
            (doto
              (pm/pixmap 1 1)
              (pm/set-color! 1 1 1 1)
              (pm/fill))))

(defonce p
         @(on-render-thread (pixel)))

(defonce b
         @(on-render-thread (batch)))

(defonce font
         @(on-render-thread (bitmap-font)))

(defonce tex
         @(on-render-thread (texture "examples/clojure-icon.png")))

(def cam (ortho-cam 1024 768))

;;renderer 1
(every-frame
  (clear!)
  (with-batch b
    (draw-quad! p 32 32 32 32)))

(every-frame
  (clear!)
  (with-batch b
    (with-color color/green
      (draw-quad! p 32 32 32 32))))

(every-frame
  (clear!)
  (with-batch b
    (with-camera cam
     (draw-quad! p 0 0 32 32))))

(every-frame
  (clear!)
  (with-batch b
    (with-font font
      (with-camera cam
        (draw-quad! p 0 0 32 32)
        (with-font-color color/yellow
          (draw-text! "foo bar qux" 0 0))
        (draw-text-wrapped! "bla\nbla\nblaaaaaaaaaa" 0 -32 32)))))

(every-frame
  (clear!)
  (with-batch b
    (draw-quad! tex 0 0 100 100)))

(every-frame
  (clear!)
  (with-batch b
    (with-color color/olive
      (draw-point! tex 0 0))))