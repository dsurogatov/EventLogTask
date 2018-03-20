<template>
    <div>
        <form-title :title="title" />
        <b-form-group id="fieldsetName" label="Название" label-for="inputName" class="text-left">
            <b-form-input id="inputName" name="inputName" v-model.trim="eventLog.name" v-validate="'required|max:200'" />
            <span class="text-danger" v-show="errors.has('inputName')">{{ errors.first('inputName') }}</span>
        </b-form-group>
        <b-form-group id="fieldsetDescr" label="Описание" label-for="inputDescr" class="text-left">
            <b-form-input id="inputDescr" name="inputDescr" v-model.trim="eventLog.description" v-validate="'required|max:4000'" />
            <span class="text-danger" v-show="errors.has('inputDescr')">{{ errors.first('inputDescr') }}</span>
        </b-form-group>
        <div class="float-right">
            <b-button-group>
                <b-button variant="primary" @click="clickSaveHandler()" :disabled="isFormInvalid">
                    Сохранить
                </b-button>
                <b-button @click="clickCancelHandler()">Отменить</b-button>
            </b-button-group>
        </div>
    </div>
</template>

<script type = "text/javascript" >
import router from '@/router'

import FormTitle from './core/FormTitle'

import { Validator } from './mixins/Validate'
import { Alerts } from './mixins/Alerts'

import eventLogService from '@/services/EventLogService'

import { Constants } from '@/app-constant'

const FakeId = Constants.Main.FakeId
const FormMode = Constants.FormMode

export default {
  name: 'EventLogForm',
  mixins: [Validator, Alerts],
  components: {
    FormTitle
  },
  data: function() {
    return {
      eventLog: { id: FakeId, name: '', description: '' },
      returnToId: FakeId
    }
  },
  computed: {
    title: function() {
      return this.isInEditMode ? 'Редактируем лог' : 'Создаем лог'
    },
    isInEditMode: function() {
      return this.$route.params.mode === FormMode.Edit
    }
  },
  methods: {
    clickSaveHandler: function() {
      if (this.isInEditMode) {
        this.update()
      } else {
        this.create()
      }
    },
    clickCancelHandler: function() {
      this.redirectToEventLog()
    },
    redirectToEventLog: function() {
      router.push({
        name: 'EventLog',
        query: { eventLogId: this.returnToId }
      })
    },
    getEventLog: function() {
      var self = this
      eventLogService
        .getOne(self.returnToId)
        .then(function(response) {
          self.eventLog = response.data
        })
        .catch(function(error) {
          if (error.response) {
            self.showErrorResponse(error.response)
          }
        })
    },
    create: function() {
      var self = this
      eventLogService
        .create(self.eventLog)
        .then(function(response) {
          self.returnToId = response.data
          self.redirectToEventLog()
        })
        .catch(function(error) {
          if (error.response) {
            self.showErrorResponse(error.response)
          }
        })
    },
    update: function() {
      var self = this
      eventLogService
        .update(self.eventLog)
        .then(function(response) {
          self.redirectToEventLog()
        })
        .catch(function(error) {
          if (error.response) {
            self.showErrorResponse(error.response)
          }
        })
    }
  },
  created: function() {
    var self = this
    self.returnToId = self.$route.params.id
    if (self.isInEditMode) {
      self.getEventLog()
    }
    // сделать делете
  }
}
</script>
