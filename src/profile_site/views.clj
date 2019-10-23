(ns profile-site.views
  (:require [garden.core :as gc]
            [hiccup.core :as hc]
            [profile-site.style :as pss]
            [profile-site.utils :refer :all]))

(defn layout [title & content]
  (hc/html [:html
            [:head
             [:style (pss/style (pss/set-page-style pss/profile-style pss/navigation-menu-style))]
             [:meta {:charset "utf-8"}]
             [:title title]]
            [:body content
             [:script {:src "/assets/listener.js"}]
              ]]))

(defn get-cardinality [attr]
  (cond
    (or (= (keyword (*get attr :attr)) :identifier) (= (keyword (*get attr :attr)) :name) (= (keyword (*get attr :attr)) :address)
        (= (keyword (*get attr :attr)) :given))
    (if (*get attr :isRequired)
      "1..*"
      "0..*")
    (or (= (keyword (*get attr :attr)) :gender) (= (keyword (*get attr :attr)) :system) (= (keyword (*get attr :attr)) :value)
        (= (keyword (*get attr :attr)) :birthDate) (= (keyword (*get attr :attr)) :use) (= (keyword (*get attr :attr)) :kladr)
        (= (keyword (*get attr :attr)) :district) (= (keyword (*get attr :attr)) :city) (= (keyword (*get attr :attr)) :line)
        (= (keyword (*get attr :attr)) :active) (= (keyword (*get attr :attr)) :family) (= (keyword (*get attr :attr)) :telecom)
        (= (keyword (*get attr :attr)) :seria))
    (if (*get attr :isRequired)
      "1..1"
      "0..1")
    :else nil))

(defn get-icon [attr]
  (cond
    (or (= (keyword (*get-in attr [0 :attr])) :address) (= (keyword (*get-in attr [0 :attr])) :identifier) (= (keyword (*get-in attr [0 :attr])) :name))
    "/assets/icon_datatype.gif"
    (> (count (*get attr 1)) 0)
    "/assets/icon_element.gif"
    :else "/assets/icon_primitive.png"))

(defn set-last-item-img [items]
  (let [last-item (last items)
        outer-item (nth (last items) 1)]
    (-> items
        (assoc-in [(.indexOf items last-item)] (->> last-item
                                                    (filter #(if (= (get-in % [1 1 1 :class]) "line-inner-item") %))
                                                    (map #(assoc-in % [1 1 3 1 :src] "/assets/tbl_blank.png"))
                                                    (concat [:div.tbody outer-item])
                                                    (vec)))
        (assoc-in (conj ((comp vec cons) (.indexOf items last-item) (vector-first-path #(= % {:src "/assets/tbl_vjoin.png",
                                                                                               :style "vertical-align: top; background-color: white;"}) last-item)) :src)
                   "/assets/tbl_vjoin_end.png")
        (assoc-in [(.indexOf items last-item) 1 1 1 1 :style] (when (> (count (rest last-item)) 1)
                                                              "background-image: url(/assets/tbl_bck010.png)")))))

(defn set-last-inner-item-img [items]
  (when (> (count items) 0)
    (-> items
        (assoc-in [(.indexOf items (last items)) 1 1 4 1 :src] "/assets/tbl_vjoin_end.png")
        (assoc-in [(.indexOf items (last items)) 1 1 1 :style] "background-color: white; background-image: url(/assets/tbl_bck100.png)"))))

(def menu
  [:div.root
   ;;[:script {:src "assets/js/listener.js"}]
   [:div.common-design
    ;; [:div.heading-segment
    ;;  [:div.heading-logo
    ;;   [:a.logolink {:href "/"}
    ;;    [:img.fhir-image {:src "http://www.hl7.org/fhir/us/core/assets/images/fhir-logo-www.png"}]]]
    ;;  [:div.logo-border]
    ;;  [:div.heading-content]]
    [:div.whole-content-body
     [:div.left-side
      [:div.heading
      [:div.heading-logo
       [:a.logolink {:href "/"}
        [:img.fhir-image {:src "http://www.hl7.org/fhir/us/core/assets/images/fhir-logo-www.png"}]]]]
      [:div.left-menu
       [:div.lmenu-item
        [:a {:href "/"} "Main Page"]]
       [:div.lmenu-item.dropdown-btn
        [:a ;;{:href "/profiles"}
         "Profile Pages"]
        [:svg.drop-down-list-icon {:height "1em" :width "1em" :fill "none" :viewBox "0 0 24 24" :stroke-width "2" :stroke-linecap "round" :stroke "currentColor"}
         [:g [:polyline {:points "9 18 15 12 9 6"}]]]]
       [:div.dropdown-container
        [:div.lmenu-item.lmenu-add-items
         [:a {:href "/profiles/Patient"} "Patient"]]
        [:div.lmenu-item.lmenu-add-items
         [:a {:href "/profiles/Organization"} "Organization"]]
        [:div.lmenu-item.lmenu-add-items
         [:a {:href "/profiles"} "Practitioner"]]]
       [:div.lmenu-item.dropdown-btn
        [:a ;;{:href "/Terminology"}
         "Terminology"]
        [:svg.drop-down-list-icon {:height "1em" :width "1em" :fill "none" :viewBox "0 0 24 24" :stroke-width "2" :stroke-linecap "round" :stroke "currentColor"}
         [:g [:polyline {:points "9 18 15 12 9 6"}]]]]
       [:div.dropdown-container
        [:div.lmenu-item.lmenu-add-items
         [:a {:href "/"}"Patient Identifiers"]]]]]
     [:div.body-container
      [:div.body-header
       [:h1 "Resource structure"]]
      [:div {:class "body-content"}]]]]])

(defn home-page []
  (let [hm [:h1 "Home page"]]
    (with-meta (assoc-in menu (vector-first-path #(= {:class "body-content"} %) menu) hm) {:title "Home"})))

(defn home-page->html []
  (let [page-title (meta (home-page))
        hm-page (home-page)]
    (layout (:title page-title) hm-page)))

(defn inner-attrs->hc [attr]
  (letfn [(into-hc [itm]
            [:div.row
             [:div.col
              [:div (assoc {:class "line-inner-item"} :style (when (> (count (*get itm 1)) 0)
                                                         "background-image: url(/assets/tbl_bck11.png)"))
               [:img {:src "/assets/tbl_spacer.png"
                      :style "vertical-align: top; background-color: white;"}]
               [:img {:src "/assets/tbl_vline.svg"
                      :style "vertical-align: top; background-color: white"}]
               [:img {:src "/assets/tbl_vjoin.png"
                      :style "vertical-align: top; background-color: white;"}]
               [:img {:src (get-icon itm)
                      :class "table-icon"}]
               [:a (*get itm :attr)]]]
             [:div.col
              [:div.line-item
               [:span.flag-item
                "S"]]]
             (let [card (get-cardinality itm)]
               (if (or (= card "1..1") (= card "1..*"))
                 [:div.col
                  [:div.line-item
                   card]]
                 [:div.col
                  [:div.line-item {:style "opacity: 0.4"}
                   card]]))
             [:div.col
              [:div.line-item {:style "opacity: 0.4"}
               (*get itm :type)]]
             [:div.col
              [:div.line-item
               [:a (*get itm :desc)]]]])

          (into-hc-comp [itm]
            (let [tr-hc (into-hc itm)]
              (assoc-in tr-hc (conj (vector-first-path #(= % {:class "line-inner-item"}) tr-hc) :style) "background-image: url(/assets/tbl_bck111.png)")))

          (add-vline [itm]
            (insert-into (*get itm 1) 2
                         [:img {:src "/assets/tbl_vline.svg"
                                :style "vertical-align: top; background-color: white"}]))]

    (map (fn [inner] (if (sequential? inner)
                       (vec (concat (into-hc-comp (first inner)) (inner-attrs->hc inner)))
                       (into-hc inner)))
         (first (rest attr)))))

(defn outter-attrs->hc [profile]
  (set-last-item-img (vec (map (fn [itm]
                                 (vec (concat [:div.tbody [:div.row
                                                           [:div.col
                                                            [:div (assoc {:class "line-item"} :style (when (> (count (*get itm 1)) 0)
                                                                                                       "background-image: url(/assets/tbl_bck11.png)"))
                                                             [:img {:src "/assets/tbl_spacer.png"
                                                                    :style "vertical-align: top; background-color: white;"}]
                                                             [:img {:src "/assets/tbl_vjoin.png"
                                                                    :style "vertical-align: top; background-color: white;"}]
                                                             [:img {:src (get-icon itm)
                                                                    :class "table-icon"}]
                                                             [:a (*get-in itm [0 :attr])]]]
                                                           [:div.col
                                                            [:div.line-item
                                                             [:span.flag-item
                                                              "S"]]]
                                                           (let [card (get-cardinality (*get itm 0))]
                                                             (if (or (= card "1..1") (= card "1..*"))
                                                               [:div.col
                                                                [:div.line-item
                                                                 card]]
                                                               [:div.col
                                                                [:div.line-item {:style "opacity: 0.4"}
                                                                 card]]))
                                                           [:div.col
                                                            [:div.line-item {:style "opacity: 0.4"}
                                                             (*get-in itm [0 :type])]]
                                                           [:div.col
                                                            [:div.line-item
                                                             [:a (*get-in itm [0 :desc])]]]]]  (set-last-inner-item-img (vec (inner-attrs->hc itm))))))
                               (get-profile-attrs profile)))))

(defn profile [{resourceType :resourceType :as resource}]
  (let [prl ^:title (-> [:div.table
                               [:div.row {:style "border: 1px #F0F0F0 solid;
                                  font-size: 11px;
                                  font-family: verdana;
                                  vertical-align: top;"}
                                [:div.col
                                 [:div.th
                                  [:a "Имя"]]]
                                [:div.col
                                 [:div.th
                                  [:a "Флаги"]]]
                                [:div.col
                                 [:div.th
                                  [:a "Кард."]]]
                                [:div.col
                                 [:div.th
                                  [:a "Тип"]]]
                                [:div.col
                                 [:div.th
                                  [:a "Описание и ограничения"]]]]
                               [:div.row
                                [:div.col
                                 [:div.line-item-resource-type
                                  [:img {:src "/assets/icon_element.gif"
                                         :style "vertical-align: top"}]
                                  (*get resource :resourceType)]]
                                [:div.col
                                 [:div.line-item
                                  ""]]
                                [:div.col
                                 [:div.line-item {:style "opacity: 0.4"}]
                                 "0..*"]]]
                        (concat (outter-attrs->hc resource))
                        (vec))]
    (with-meta prl {:title resourceType})
    prl))

(defn profile-page [resource menu]
  (let [prl (profile resource)]
    (-> menu
        ;;(assoc-in [(.indexOf menu (last menu)) (.indexOf (last menu) [:div {:class "body-content"}]) 2] prl)
        (assoc-in (vector-first-path #(= % {:class "body-content"}) menu) prl)

        (with-meta {:title (get resource :resourceType)}))))

(defn profile-page->html [resource]
  (let [page-title (meta (profile-page resource menu))
        pt-page (profile-page resource menu)]
    (layout (:title page-title) pt-page)))
