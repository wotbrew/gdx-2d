(defproject gdx-2d "0.1.0-SNAPSHOT"
  :description "2d graphics api for libgdx"
  :url "http://github.com/danstone/gdx-2d"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.badlogicgames.gdx/gdx "1.4.1"]
                 [com.badlogicgames.gdx/gdx-backend-lwjgl "1.4.1"]
                 [com.badlogicgames.gdx/gdx-platform "1.4.1"
                  :classifier "natives-desktop"]]
  :profiles {:dev {:dependencies [[gdx-loopy "0.1.0-SNAPSHOT"]]
                   :source-paths ["examples"]}}
  :global-vars {*warn-on-reflection* true}
  :repositories [["sonatype"
                  "https://oss.sonatype.org/content/repositories/releases/"]])
