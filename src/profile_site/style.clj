(ns profile-site.style
  (:require [garden.core :as gc]))

(defn style [data]
  (gc/css data))

(def profile-style [[:.line-item
                     {:vertical-align "top"
                      :text-align "left"
                      :padding "0px 4px 0px 4px"
                      :background-color "white"}]

                    [:.line-item-resource-type
                     {:vertical-align "top"
                      :text-align "left"
                      :padding "0px 4px 0px 4px"}]

                    [:ul
                     {:padding-left "20px"
                      :margin "0px"
                      :width "70%"}]

                    [:li
                     {:margin-bottom "10px"}]

                    [:.line-inner-item
                     {:vertical-align "top"
                      :text-align "left"
                      :padding "0px 4px 0px 4px"
                      :background-image "url(/assets/tbl_bck110.png)"}]

                    [:table
                     {:font-size "11px"
                      :font-family "verdana"
                      :max-width "100%"
                      :border-spacing "0px"
                      :border-collapse "collapse"}]

                    [:a#list-item
                     {:color "#666"}]

                    [:a#list-item:hover
                     {:text-decoration "underline"}]

                    [:a
                     {:text-decoration "none"}]

                    [:td
                     {:line-height "1em"}]

                    [:tbody
                     {:display "table-row-group"}]

                    [:.flag-item
                     {:padding-left "3px"
                      :padding-right "3px"
                      :color "white"
                      :background-color "red"}]

                    [:th
                     {:vertical-align "top"
                      :text-align "left"
                      :background-color "white"
                      :border "0px #F0F0F0 solid"
                      :padding "0px 4px 0px 4px"}]

                    [:.table-icon
                     {:vertical-align "top"
                      :margin "0px 2px 0px 0px"
                      :background-color "white"}]

                    [:body
                     {:display "flex"
                      :flex-direction "column"
                      :font-family "'Roboto', sans-serif"
                      :margin "0"
                      :color "#212529"
                      :line-height "1.5"
                      :text-align "left"}]

                    [:.logo :.img-logo
                     {:height "30px"}]

                    [:.resource-list
                     {:padding-left "15px"
                      :padding-top "7px"
                      :margin-left "0px"}]

                    [:.terminology-list
                     {:padding-left "15px"
                      :padding-top "7px"
                      :margin-left "0px"}]

                    [:.menu :.logo
                     {:padding "10px 50px"}]

                    [:.menu
                     {:margin "0px"
                      :padding "10px 40px"
                      :box-shadow "0 3px 8px 0 rgba(116, 129, 141, 0.1)"
                      :border-bottom "1px solid #d4dadf"
                      :list-style-position "inside"
                      :height "59px"}]

                    [:.profile
                     {:margin "20px 88px"}]

                    [:.wrap
                     {:flex-direction "row"
                      :display "flex"
                      :box-sizing "border-box"}]

                    [:.nav
                    {:padding "20px 30px"
                     :min-width "298px"
                     :background "#f5f7f9"
                     :padding-left "0"
                     :padding-top "1px"
                     :width "20px"
                     :height "min-content"
                     :border-right "1px solid #e6ecf1"}]

                    [:.ulmenu
                     {:padding-inline-start "0px"
                      :padding "7px 24px 5px 16px"
                      :line-height "35px"}]])
