<template>
  <div>
    <form-title title="Считаем..." />
    <event-log-select @selectEventLog="handleEventLogSelected" :passedEventLogIdFromPath="passedEventLogIdFromPath" />
    <event-log-add-value :selected-event-log="selectedEventLog" />
    <event-log-summary :selected-event-log="selectedEventLog" />
  </div>
</template>

<script type = "text/javascript" >
import EventLogSelect from './EventLogSelect'
import EventLogAddValue from './EventLogAddValue'
import EventLogSummary from './EventLogSummary'
import FormTitle from './core/FormTitle'

import { Constants } from '@/app-constant'

export default {
  name: 'EventLog',
  components: {
    EventLogSelect,
    EventLogAddValue,
    EventLogSummary,
    FormTitle
  },
  data: function() {
    return {
      selectedEventLog: {
        id: Constants.FakeId,
        name: ''
      }
    }
  },
  computed: {
    passedEventLogIdFromPath: function() {
      var self = this
      if (self.$route.query.eventLogId) {
        return self.$route.query.eventLogId
      } else {
        return Constants.FakeId
      }
    }
  },
  methods: {
    handleEventLogSelected: function(eventLog) {
      this.selectedEventLog = eventLog
    }
  }
}
</script>
<style>

</style>
