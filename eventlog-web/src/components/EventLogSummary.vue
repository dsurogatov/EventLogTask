<template>
  <div id="summary" class="border border-secondary rounded">
    <h4>Итого</h4>
    <b-container>
      <b-row>
        <b-col>Количество событий: {{ summary.countItems }}.</b-col>
        <b-col>Сумма: {{ summary.sumValues }}.</b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script type = "text/javascript" >
import { EventBus } from './event-bus.js'
import eventLogSummaryService from '@/services/EventLogSummaryService'

export default {
  name: 'EventLogSummary',
  props: ['selectedEventLog'],
  data: function() {
    return {
      summary: {}
    }
  },
  watch: {
    selectedEventLog: function(newValue) {
      this.getEventLogSummary()
    }
  },
  methods: {
    getEventLogSummary: function() {
      var self = this
      eventLogSummaryService
        .getSummary(self.selectedEventLog.id)
        .then(function(response) {
          self.summary = response.data
        })
        .catch(function(error) {
          console.log(error)
        })
    }
  },
  created: function() {
    var self = this
    EventBus.$on('add-value-to-event-log', addingvalue => {
      self.getEventLogSummary()
    })
  },
  destroyed: function() {
    EventBus.$off('add-value-to-event-log')
  }
}
</script>

<style scoped>
#summary {
  padding-top: 10px;
  padding-bottom: 10px;
}
</style>
