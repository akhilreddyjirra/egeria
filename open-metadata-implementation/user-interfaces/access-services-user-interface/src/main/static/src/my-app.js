/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */

import {PolymerElement, html} from '@polymer/polymer/polymer-element.js';
import {setPassiveTouchGestures, setRootPath} from '@polymer/polymer/lib/utils/settings.js';
import {mixinBehaviors} from "@polymer/polymer/lib/legacy/class.js";
import {AppLocalizeBehavior} from "@polymer/app-localize-behavior/app-localize-behavior.js";
import '@polymer/app-layout/app-drawer/app-drawer.js';
import '@polymer/app-layout/app-drawer-layout/app-drawer-layout.js';
import '@polymer/app-layout/app-header/app-header.js';
import '@polymer/app-layout/app-header-layout/app-header-layout.js';
import '@polymer/app-layout/app-scroll-effects/app-scroll-effects.js';
import '@polymer/app-layout/app-toolbar/app-toolbar.js';
import '@polymer/app-route/app-location.js';
import '@polymer/app-route/app-route.js';
import '@polymer/iron-pages/iron-pages.js';
import '@polymer/iron-selector/iron-selector.js';
import '@polymer/iron-localstorage/iron-localstorage';
import '@polymer/paper-icon-button/paper-icon-button.js';
import '@polymer/paper-menu-button/paper-menu-button.js';
import '@polymer/paper-item/paper-item.js';
import '@polymer/paper-button';
import '@polymer/paper-dropdown-menu/paper-dropdown-menu.js';
import '@polymer/paper-listbox/paper-listbox.js';
import '@polymer/paper-item/paper-item.js';
import '@polymer/paper-item/paper-item.js';
import '@polymer/paper-input/paper-input.js';
import '@polymer/iron-icon/iron-icon.js';
import '@polymer/iron-icons/iron-icons.js';
import '@polymer/iron-form/iron-form.js';
import './my-icons.js';
import './token-ajax';
import './login-view.js';
import './user-options-menu';
import './shared-styles';

// Gesture events like tap and track generated from touch will not be
// preventable, allowing for better scrolling performance.
setPassiveTouchGestures(true);

// Set Polymer's root path to the same value we passed to our service worker
// in `index.html`.
setRootPath(MyAppGlobals.rootPath);

class MyApp extends mixinBehaviors([AppLocalizeBehavior], PolymerElement) {
    static get template() {
        return html`
      <style include="shared-styles">
        :host {
           display: block;
        };
        app-drawer-layout:not([narrow]) [drawer-toggle] {
          display: none;
        };
        app-header {
          color: #fff;
          background-color: var(--app-primary-color);
        };
        app-header paper-icon-button {
          --paper-icon-button-ink-color: white;
        };
        .drawer-list {
          margin: 0 20px;
        };
        .drawer-list a {
          display: block;
          padding: 0 16px;
          text-decoration: none;
          color: var(--app-secondary-color);
          line-height: 40px;
        };
        .drawer-list-selected,
        .drawer-list div.iron-selected {
          font-weight: bold;
          color: var(--app-secondary-color);
          background-color: var(--app-primary-color);
        };
        
        paper-input.custom:hover {
          border: 1px solid #29B6F6;
        };
        paper-input.custom {
          margin-bottom: 14px;
          --primary-text-color: #01579B;
          --paper-input-container-color: black;
          --paper-input-container-focus-color: black;
          --paper-input-container-invalid-color: black;
          border: 1px solid #BDBDBD;
          border-radius: 5px;
    
          /* Reset some defaults */
          --paper-input-container: { padding: 0;};
          --paper-input-container-underline: { display: none; height: 0;};
          --paper-input-container-underline-focus: { display: none; };
    
          /* New custom styles */
          --paper-input-container-input: {
            box-sizing: border-box;
            font-size: inherit;
            padding: 4px;
          };
          --paper-input-container-input-focus: {
            background: rgba(0, 0, 0, 0.1);
          };
          --paper-input-container-input-invalid: {
            background: rgba(255, 0, 0, 0.3);
          };
          --paper-input-container-label: {
            top: -8px;
            left: 4px;
            background: white;
            padding: 2px;
            font-weight: bold;
          };
          --paper-input-container-label-floating: {
            width: auto;
         };
        
      </style>
      <iron-localstorage name="my-app-storage" value="{{token}}"></iron-localstorage>

      <app-location route="{{route}}" url-space-regex="^[[rootPath]]" use-hash-as-path></app-location>

      <app-route route="{{route}}" pattern="[[rootPath]]:page" data="{{routeData}}" tail="{{subroute}}"></app-route>

        <template is="dom-if" if="[[!token]]"  restamp="true">
            <login-view id="loginView" token="{{token}}"></login-view>
        </template>
      
        <template is="dom-if" if="[[token]]"  restamp="true">
        
            <app-drawer-layout id="drawerLayout" flex forceNarrow  narrow="{{narrow}}" fullbleed="">
                <app-drawer id="drawer" slot="drawer"  swipe-open="[[narrow]]">
                  <img src="../images/Logo_trademark.jpg" height="60" style="margin: auto; display: block; margin-top: 15pt;"/>
                  <iron-selector selected="[[page]]" attr-for-selected="name" 
                        class="drawer-list" swlectedClass="drawer-list-selected" role="navigation">                  
                    <div name="asset-search" language="[[language]]"><a href="[[rootPath]]#/asset-search">Asset search</a></div>
                    <div name="asset-lineage"><a href="[[rootPath]]#/asset-lineage">Asset Lineage</a></div>
                    <div name="subject-area"><a href="[[rootPath]]#/subject-area">Subject Area</a></div>
                  </iron-selector>
                 
                 
                </app-drawer>
    
                <!-- Main content-->
                <app-header-layout>
        
                  <app-header slot="header" condenses="" reveals="" effects="waterfall">
                    <app-toolbar>
                      <paper-icon-button on-tap="_toggleDrawer" id="toggle" icon="menu"></paper-icon-button>
                      <template is="dom-if" if="[[narrow]]" >
                        <img src="../images/logo-white.png" style="vertical-align: middle; max-height: 80%; margin-left: 15pt; margin-right: 15pt; display: inline-block; "/>
                      </template>
                      <div>
                        <template is="dom-if" if="[[!narrow]]" >
                            Open Metadata -
                        </template>
                        [[page]]
                      </div>
                      
                      <div main-title="">
<!--                        <div style="margin-left: 100pt; width: 300pt">-->
<!--                            <iron-form id="searchForm">-->
<!--                                <form method="get">-->
<!--                                    <iron-a11y-keys keys="enter" on-keys-pressed="_search"></iron-a11y-keys>-->
<!--                                    <paper-input class="custom" label="Search" value="{{q}}" no-label-float required autofocus>-->
<!--                                        <iron-icon icon="search" slot="prefix" class="icon"></iron-icon>-->
<!--                                    </paper-input>-->
<!--                                </form>-->
<!--                           </iron-form>-->
<!--                        </div>-->
                      </div>
                      <div style="float: right"><user-options token="[[token]]"></user-options></div>
                      
                    </app-toolbar>
                  </app-header>
        
                  <iron-pages selected="[[page]]" attr-for-selected="name" role="main">
                    <asset-search-view language="[[language]]" name="asset-search"></asset-search-view>
                    <subject-area-view language="[[language]]" name="subject-area"></subject-area-view>
                    <asset-lineage-view language="[[language]]" name="asset-lineage"></asset-lineage-view>
                    <my-view404 name="view404"></my-view404>
                  </iron-pages>
                  
                </app-header-layout>
            </app-drawer-layout>
            
         </template>
    `;
    }

    static get properties() {
        return {
            language: { value: 'en' },

            page: {
                type: String,
                reflectToAttribute: true,
                observer: '_pageChanged'
            },
            token: {
                type: Object,
                notify: true,
                observer: '_tokenChanged'
            },
            routeData: Object,
            subroute: Object,
            pages: {
                type: Array,
                value: ['asset-search', 'subject-area', 'asset-lineage']
            },
            feedback: {
                type: Object,
                notify: true,
                observer: '_feedbackChanged'
            }
        };
    }

    static get observers() {
        return [
            '_routePageChanged(routeData.page)'
        ];
    }

    ready(){
        super.ready();
        this.addEventListener('logout', this._onLogout);
        this.addEventListener('open-page', this._onPageChanged);
        this.addEventListener('show-feedback', this._onFeedbackChanged);
        this.addEventListener('set-title', this._onSetTitle);
    }

    _getDrawer(){
        var dL = this.shadowRoot.querySelector('#drawerLayout');
        if(dL){
            return dL.drawer;
        }
        return;
    }

    _toggleDrawer() {
        var dL = this.shadowRoot.querySelector('#drawerLayout');
        if (dL.forceNarrow || !dL.narrow) {
            dL.forceNarrow = !dL.forceNarrow;
        } else {
            dL.drawer.toggle();
        }
    }

    _routePageChanged(page) {
        // Show the corresponding page according to the route.
        //
        // If no page was found in the route data, page will be an empty string.
        // Show 'asset-search' in that case. And if the page doesn't exist, show 'view404'.

        if (!page) {
            this.page = 'asset-search';
        } else if (this.pages.indexOf(page) !== -1) {
            this.page = page;
        } else {
            this.page = 'asset-search';
        }

        // Close a non-persistent drawer when the page & route are changed.
        var drawer = this._getDrawer();
        if (this.page!='login' && drawer && !drawer.persistent) {
            this._getDrawer().close();

        }
    }

    _onPageChanged(event) {
        this.page = event.detail.page;
    }

    _onLogout(event) {
        console.log('removing token:');
        //TODO invalidate token from server
        console.log('LOGOUT: removing token...');
        this.token = null;
    }

    _hasToken(){
        return typeof this.token  !== "undefined" && this.token != null;
    }

    _tokenChanged(newValue, oldValue) {
        console.debug('token changed from: '+ oldValue +' \nto new value: ' + newValue)
    }

    _pageChanged(page) {
        // Import the page component on demand.
        //
        // Note: `polymer build` doesn't like string concatenation in the import
        // statement, so break it up.
        console.log(page);
        switch (page) {
            case 'subject-area':
                import('./subject-area/subject-area-view.js');
                break;
            case 'asset-lineage':
                import('./asset-search/asset-lineage-view.js');
                break;
            case 'view404':
                import('./asset-search/my-view404.js');
                break;
            case 'asset-search' :
                import('./asset-search/asset-search-view.js');
                break;
        }
    }



    attached() {
        this.loadResources(
            // The specified file only contains the flattened translations for that language:
            'locales/en.json',  //e.g. for es {"hi": "hola"}
            'en',               // unflatten -> {"es": {"hi": "hola"}}
            true                // merge so existing resources won't be clobbered
        );
    }
}

window.customElements.define('my-app', MyApp);
