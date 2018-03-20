<template>
  <div>
    <form-title title="Статистика" />
    <event-log-select @selectEventLog="handleEventLogSelected" hideEditButtons="true" />
    <date-span-selector @changeDateSpan="handleDateSpanChanged" />
    <div class="float-left mt-3 mb-3">
      <b-button variant="primary" :disabled="!isFilterValid" @click="handleRefreshBtnClick">Обновить</b-button>
    </div>
    <b-table striped hover :items="items" :fields="cols"></b-table>
  </div>
</template>

<script>
import FormTitle from './core/FormTitle'
import EventLogSelect from './EventLogSelect'
import DateSpanSelector from './DateSpanSelector'

import eventLogSummaryService from '@/services/EventLogSummaryService'

import { Alerts } from './mixins/Alerts'

import { Constants } from '@/app-constant'

var moment = require('moment')

export default {
  name: 'EventLogStatistics',
  mixins: [Alerts],
  components: {
    FormTitle,
    EventLogSelect,
    DateSpanSelector
  },
  data: function() {
    return {
      selectedEventLog: {
        id: Constants.FakeId,
        name: ''
      },
      dateSpan: {
        from: '',
        to: ''
      },
      cols: {
        date: {
          label: 'Дата',
          formatter: function(cell) {
            return moment(cell).format('DD.MM.YYYY')
          }
        },
        countItems: {
          label: 'Число событий'
        },
        sumValues: {
          label: 'Сумма'
        }
      },
      items: []
    }
  },
  computed: {
    isFilterValid: function() {
      if (this.selectedEventLog.id === Constants.FakeId) {
        return false
      }
      if (!moment(this.dateSpan.from).isValid()) {
        return false
      }
      if (!moment(this.dateSpan.to).isValid()) {
        return false
      }
      if (moment(this.dateSpan.from).isAfter(moment(this.dateSpan.to))) {
        return false
      }
      return true
    }
  },
  methods: {
    handleEventLogSelected: function(eventLog) {
      this.selectedEventLog = eventLog
    },
    handleDateSpanChanged: function(dateSpan) {
      this.dateSpan = dateSpan
    },
    handleRefreshBtnClick: function() {
      var self = this
      eventLogSummaryService
        .getSummaryReport(
          this.selectedEventLog.id,
          this.dateSpan.from,
          this.dateSpan.to
        )
        .then(function(response) {
          self.items = response.data
        })
        .catch(function(error) {
          if (error.response) {
            self.showErrorResponse(error.response)
          }
        })
    }
  },
  created: function() {
    setTimeout(() => {
      console.log(this.isFilterValid)
      if (this.isFilterValid) {
        this.handleRefreshBtnClick()
      }
    }, 500)
  }
}
</script>
