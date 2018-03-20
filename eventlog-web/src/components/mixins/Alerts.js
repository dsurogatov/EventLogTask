import { EventBus } from '@/components/event-bus.js'

export const Alerts = {
  methods: {
    showErrorResponse: function(response) {
      EventBus.$emit('open-message', {
        title: 'There is an error on the backend!',
        message: response.data.message,
        type: 'error'
      })
    }
  }
}
