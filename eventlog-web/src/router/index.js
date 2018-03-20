import Vue from 'vue'
import Router from 'vue-router'
import EventLog from '@/components/EventLog'
import EventLogStatistics from '@/components/EventLogStatistics'
import EventLogForm from '@/components/EventLogForm'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'EventLog',
      component: EventLog
    },
    {
      path: '/statistics',
      name: 'EventLogStatistics',
      component: EventLogStatistics
    },
    {
      path: '/form/:mode/id/:id',
      name: 'EventLogForm',
      component: EventLogForm
    },
    {
      path: '*',
      component: {
        template: '<h1>not found</h1>'
      }
    }
  ]
})
