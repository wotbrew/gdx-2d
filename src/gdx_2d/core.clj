(ns gdx-2d.core
  (:import (com.badlogic.gdx.graphics.g2d SpriteBatch BitmapFont TextureRegion)
           (com.badlogic.gdx.math Matrix4)
           (com.badlogic.gdx.graphics Camera GL20 Color Texture OrthographicCamera)
           (com.badlogic.gdx Gdx)
           (com.badlogic.gdx.files FileHandle))
  (:require [gdx-2d.pixmap :as pixmap]
            [gdx-2d.color :as color]))

(defn fps
  "Get the current frames per second"
  []
  (. Gdx/graphics getFramesPerSecond))

(defn delta
  "Get the current delta time"
  []
  (. Gdx/graphics getDeltaTime))

(defn batch
  "Creates a new sprite batch instance.

   Must be performed on the render thread."
  []
  (SpriteBatch.))

(defprotocol IFileHandleable
  (^FileHandle file-handle [this] "creates a new file handle"))

(extend-protocol IFileHandleable
  FileHandle
  (file-handle [this]
    this)
  String
  (file-handle [this]
    (FileHandle. this)))

(defn bitmap-font
  "Creates a new bitmap font.
   If no argument supplied, creates the default font.

   Must be performed on the render thread."
  ([]
   (BitmapFont.))
  ([file]
   (BitmapFont. (file-handle file))))

(defn texture
  "Creates a new texture from a file

   Must be performed on the render thread"
  ([file]
   (Texture. (file-handle file))))

(defn ortho-cam
  "Creates a new orthographic camera"
  [width height]
  (OrthographicCamera. width height))

(def ^:dynamic ^SpriteBatch *batch*)
(def ^:dynamic ^BitmapFont *font*)

(defmacro with-batch
  "Perform the body with using the given sprite batch"
  [batch & body]
  `(binding [*batch* ~batch]
    (try
      (.begin *batch*)
      ~@body
      (catch Exception e#
        (throw e#))
      (finally
        (.end *batch*)))))

(defmacro with-font
  "Perform the body using the given bitmap font"
  [^BitmapFont font & body]
  `(binding [*font* ~font]
     ~@body))

(defn clear!
  "Clears the screen"
  []
  (. Gdx/gl glClearColor 0 0 0 0)
  (. Gdx/gl glClear GL20/GL_COLOR_BUFFER_BIT))

(defn combined
  [^Camera cam]
  (.combined cam))

(defmacro with-camera
  "Perform the body using the given camera"
  [camera & body]
  `(let [proj# (.cpy (.getProjectionMatrix *batch*))]
    (.setProjectionMatrix *batch* (combined ~camera))
    ~@body
    (.setProjectionMatrix *batch* proj#)))

(defn set-font-color!
  "Sets the color used for the given font"
  ([^BitmapFont font r g b a]
   (.setColor font r g b a))
  ([^BitmapFont font ^Color color]
   (.setColor font color)))

(defn set-color!
  "Sets the color used by the current sprite batch"
  ([r g b a]
   (.setColor *batch* r g b a))
  ([^Color color]
   (.setColor *batch* color)))

(defmacro with-font-color
  "Perform the body using the given font color"
  [color & body]
  `(let [old# (.getColor *font*)]
    (set-font-color! *font* ~color)
    ~@body
    (set-font-color! *font* old#)))

(defmacro with-color
  "Perform any sprite batch operations in the body using the given color"
  [color & body]
  `(let [old# (.getColor *batch*)]
     (set-color! ~color)
     ~@body
     (set-color! old#)))

(defmacro with-font
  "Performs any operations in the body using the given font"
  [font & body]
  `(binding [*font* ~font]
     ~@body))


(defprotocol IDrawQuad
  (draw-quad! [this x y width height]
              "Draw the given thing in the given rectangle"))

(extend-protocol IDrawQuad
  Texture
  (draw-quad! [this x y width height]
    (.draw *batch* this ^float x ^float y ^float width ^float height))
  TextureRegion
  (draw-quad! [this x y width height]
    (.draw *batch* this ^float x ^float y ^float width ^float height)))

(defprotocol IDrawPoint
  (draw-point! [this x y]
               "Draw the given thing at the given point"))

(defn pixel
  "Creates a basic solid 1x1 texture for use in drawing primitive shapes
   if a color is not supplied, white is used.

   Must be called on the render thread."
  ([]
   (pixel color/white))
  ([color]
   (pixmap/pixmap->texture (pixmap/pixel color))))

(extend-protocol IDrawPoint
  Texture
  (draw-point! [this x y]
    (.draw *batch* this ^float x ^float y))
  TextureRegion
  (draw-point! [this x y]
    (.draw *batch* this ^float x ^float y)))

(defn draw-text!
  "Draws text at the given point"
  ([font text x y]
   (.drawMultiLine ^BitmapFont font *batch* text ^float x ^float y))
  ([text x y]
   (draw-text! *font* text x y)))

(defn draw-text-wrapped!
  "Draws text and wraps text outside of x + w"
  ([font text x y w]
   (.drawWrapped ^BitmapFont font *batch* text x y w))
  ([text x y w]
   (draw-text-wrapped! *font* text x y w)))

