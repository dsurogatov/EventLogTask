<template>
  <div>
    <b-input-group prepend="В логе" class="mb-3">
      <b-form-select v-model="selectedEventLogId">
        <option v-for="eventLog in eventLogs" :key="eventLog.id" :value="eventLog.id">
          {{ eventLog.name }}
        </option>
      </b-form-select>
      <b-input-group-append v-show="isHideEditButtons">
        <b-btn v-b-tooltip.hover title="Добавить лог!" variant="primary" @click="addEventLog">
          <plus-icon />
        </b-btn>
        <b-btn v-b-tooltip.hover title="Редактировать лог!" variant="primary" @click="editEventLog" :disabled="isSelectedFakeId">
          <pencil-icon />
        </b-btn>
        <b-btn v-b-tooltip.hover title="Удалить лог!" variant="danger" @click="removeEventLog" :disabled="isSelectedFakeId">
          <minus-circle-outline-icon />
        </b-btn>
      </b-input-group-append>
    </b-input-group>
  </div>
</template>

<script type = "text/javascript" >
import MinusIcon from 'vue-material-design-icons/minus.vue'
import MinusCircleOutlineIcon from 'vue-material-design-icons/minus-circle-outline.vue'
import PlusIcon from 'vue-material-design-icons/plus.vue'
import PencilIcon from 'vue-material-design-icons/pencil.vue'

import router from '@/router'

import eventLogService from '@/services/EventLogService'
import { Constants } from '@/app-constant'

const FormMode = Constants.FormMode
const FakeId = Constants.Main.FakeId

export default {
  name: 'EventLogSelect',
  props: ['passedEventLogIdFromPath', 'hideEditButtons'],
  components: {
    MinusIcon,
    PlusIcon,
    PencilIcon,
    MinusCircleOutlineIcon
  },
  data: function() {
    return {
      eventLogs: [],
      selectedEventLogId: FakeId
    }
  },
  computed: {
    isSelectedFakeId: function() {
      return this.selectedEventLogId === FakeId
    },
    isHideEditButtons: function() {
      return this.hideEditButtons === undefined
    }
  },
  watch: {
    selectedEventLogId: function(newValue) {
      var selectedEventLog = this.eventLogs.find(function(eventLog) {
        return eventLog.id === newValue
      })
      if (selectedEventLog !== undefined) {
        this.$emit('selectEventLog', selectedEventLog)
      }
    }
  },
  methods: {
    getAllEventLogs: function() {
      var self = this
      eventLogService
        .getAll()
        .then(function(response) {
          self.eventLogs = response.data
          if (self.eventLogs.length > 0) {
            self.setSelectedEventLogId()
          } else {
            self.selectedEventLogId = FakeId
          }
        })
        .catch(function(error) {
          console.log(error)
        })
    },
    setSelectedEventLogId: function() {
      var self = this
      if (
        self.passedEventLogIdFromPath &&
        self.passedEventLogIdFromPath !== Constants.FakeId
      ) {
        var selectedEventLog = self.eventLogs.find(function(eventLog) {
          return eventLog.id === parseInt(self.passedEventLogIdFromPath)
        })
        if (selectedEventLog) {
          self.selectedEventLogId = selectedEventLog.id
          return
        }
      }
      self.selectedEventLogId = self.eventLogs[0].id
    },
    addEventLog: function() {
      this.openEditForm(FormMode.Add)
    },
    editEventLog: function() {
      this.openEditForm(FormMode.Edit)
    },
    removeEventLog: function() {
      var self = this
      eventLogService
        .remove(self.selectedEventLogId)
        .then(function(response) {
          self.$root.$emit('bv::hide::tooltip')
          self.getAllEventLogs()
        })
        .catch(function(error) {
          console.log(error)
        })
    },
    openEditForm: function(mode) {
      var self = this
      router.push({
        name: 'EventLogForm',
        params: { mode: mode, id: self.selectedEventLogId }
      })
    }
  },
  created: function() {
    this.getAllEventLogs()
  }
}
</script>

<style>

</style>
