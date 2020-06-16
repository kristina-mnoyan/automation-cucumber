@ui
Feature: As a user I want to be able to send draft messages, scheduled messages and be able to search through messages

  Background:
    Given the user is logged in the Gmail

  Scenario Outline: Search functionality for sent messages
    When the user goes to the "<messageFolderNames>" folder
    Then the last part of the URL is same as "<messageFolderNames>"
    And the user logs out from the system
    Examples:
      | messageFolderNames |
      | inbox              |
      | starred            |
      | sent               |
      | drafts             |

  Scenario: Create a draft message and send it from the appropriate folder
    When the user creates a "draft" message
    And the user clicks on the Close button
    Then the created message goes to the Draft folder
    When the user clicks on the message from the Draft folder
    And clicks on the Send button
    Then the current message is moved to Sent folder
    And the user logs out from the system


  Scenario: Send a scheduled message
    When the user creates a "scheduled" message
    When the user sets specific parameters for scheduled message
    Then the message is sent to Scheduled folder
    And is sent to recipient after the specific time set
    And the user logs out from the system

