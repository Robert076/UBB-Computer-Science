from abc import abstractmethod

class MessageSender:
        def createMessage(self, text: str):
                self.message = text
                
        @abstractmethod
        def sendMessage(self, message):
                pass
        
class PigeonSender(MessageSender):
        def sendMessage(self, message):
                print(f"{message} has been sent by a pigeon.")

class EmailSender(MessageSender):       
        def sendMessage(self, message):
                print(f"{message} has been sent by email")
                
class MessageSenderFactory:    
    @staticmethod
    def create_sender(sender_type: str) -> MessageSender:
        if sender_type == "pigeon":
            return PigeonSender()
        elif sender_type == "email":
            return EmailSender()
        else:
            raise ValueError(f"Unknown sender type: {sender_type}")

factory = MessageSenderFactory()
sender = factory.create_sender("pigeon")
sender.sendMessage("Test")